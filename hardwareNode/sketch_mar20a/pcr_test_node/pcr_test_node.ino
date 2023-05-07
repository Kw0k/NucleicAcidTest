#include <ESP8266WiFi.h>
#include <WebSocketsClient.h>
#include <ArduinoJson.h> 
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include <Fonts/FreeSerif9pt7b.h>

#define OLED_ADDR 0x3C

Adafruit_SSD1306 display(128, 64, &Wire, -1);

unsigned long lastHeartbeatMillis = 0;
const unsigned long heartbeatInterval = 5000; // 5 seconds

const char* ssid = "bydb123456789";
const char* password = "12345678";
const char* websocket_server = "localhost";
const uint16_t websocket_port = 8888;
const char* websocket_path = "/node-endpoint";

WebSocketsClient webSocket;

int material_tube_num = 0;
int staff_num = 0;
int material_swab_num = 0;
int material_alcohol_num = 0;

void sendMessage(const char* opt) {
  DynamicJsonDocument jsonDoc(1024);
  jsonDoc["apiPort"] = opt;
  JsonObject nodeLog = jsonDoc.createNestedObject("nodeLog"); 
  nodeLog["opt_id"] = 15;
  nodeLog["material_tube_num"] = material_tube_num;
  nodeLog["staff_num"] = staff_num;
  nodeLog["material_swab_num"] = material_swab_num;
  nodeLog["material_alcohol_num"] = material_alcohol_num;
  String jsonString;
  serializeJson(jsonDoc, jsonString);
  webSocket.sendTXT(jsonString);
}
void updateBaseInfoDisplay(){
    setDisplayText(">>PCR TEST SYSTEM<<");
    addDisplayText(("Stuff   Num:" + std::to_string(staff_num)).c_str(), false);
    addDisplayText(("Swab    Num:" + std::to_string(material_swab_num)).c_str(), false);
    addDisplayText(("Tube    Num:" + std::to_string(material_tube_num)).c_str(), false);
    addDisplayText(("Alcohol Num:" + std::to_string(material_alcohol_num)).c_str(), false);
}

void countDown(int seconds, void (*callback)()) {
  int remainingSeconds = seconds;

  while (remainingSeconds > 0) {
    Serial.println(remainingSeconds);
    delay(1000);
    remainingSeconds--;
  }

  if (callback != nullptr) {
    callback();
  }
}

void parsingMessage(const char* msg){
    DynamicJsonDocument jsonDoc(1024);
    DeserializationError error = deserializeJson(jsonDoc, msg);
    if (error) {
      Serial.println("Failed to parse JSON");
      return;
    }
    const char* apiPort = jsonDoc["apiPort"]; // "sync_data"
    if (strcmp(apiPort, "sync_data") == 0) {
      JsonObject nodeLog = jsonDoc["nodeLog"];
      material_tube_num = nodeLog["material_tube_num"];
      staff_num = nodeLog["staff_num"];
      material_swab_num = nodeLog["material_swab_num"];
      material_alcohol_num = nodeLog["material_alcohol_num"];
      updateBaseInfoDisplay();
  }
  if (strcmp(apiPort, "instruction") == 0){
      int Instruction = jsonDoc["instruction"];
      addDisplayText(("Receive Instruction "+std::to_string(Instruction)).c_str(), true);
      countDown(5,updateBaseInfoDisplay);
  }
}

void sendHeartbeatMessage() {
  StaticJsonDocument<64> jsonDoc;
  jsonDoc["apiPort"] = "heartbeat";
  String message;
  serializeJson(jsonDoc, message);
  webSocket.sendTXT(message);
}
int textStarY = 0;
void setDisplayText(const char* text){
  display.clearDisplay();
  textStarY = 0;
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(getTextCenterX(text), textStarY);
  display.println(text);
  display.display();
}

void addDisplayText(const char* text,bool isCentered){
  textStarY += 8;
  if (isCentered) {
    display.setCursor(getTextCenterX(text), textStarY);
  } else {
    display.setCursor(0, textStarY);
  }
  display.println(text);
  display.display();
}

int getTextCenterX(const char* text) {
  int textLength = strlen(text) * 1 * 6;
  int padding = (display.width() - textLength) / 2;
  return padding;
}

void webSocketEvent(WStype_t type, uint8_t *payload, size_t length) {
  switch (type) {
    case WStype_DISCONNECTED:
      Serial.printf("[WSc] Disconnected!\n");
      digitalWrite(LED_BUILTIN, HIGH);
      break;
    case WStype_CONNECTED:
      Serial.printf("[WSc] Connected to url: %s\n", payload);
      sendMessage("RoL");
      digitalWrite(LED_BUILTIN, LOW);
      break;
    case WStype_TEXT:
      Serial.printf("[WSc] Received text: %s\n", payload);
      parsingMessage((const char*)payload);
      break;
    case WStype_BIN:
      Serial.printf("[WSc] Received binary data\n");
      break;
  }
}
void setup() {
  //init pin
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(D0, INPUT_PULLDOWN_16);
  pinMode(D6, INPUT_PULLUP); 
  pinMode(D7, INPUT_PULLUP); 
  pinMode(D8, INPUT_PULLUP); 
  //  
  Serial.begin(115200);
  display.begin(SSD1306_SWITCHCAPVCC, OLED_ADDR);
  randomSeed(analogRead(0));
  Serial.setDebugOutput(true);
  setDisplayText(">>PCR TEST SYSTEM<<");

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  webSocket.begin(websocket_server, websocket_port,websocket_path);
  digitalWrite(LED_BUILTIN, HIGH);
  webSocket.onEvent(webSocketEvent);

  setDisplayText(">>PCR TEST SYSTEM<<");
}

void loop() {
  webSocket.loop();

  //心跳包
  if (millis() - lastHeartbeatMillis > heartbeatInterval) {
    sendHeartbeatMessage();
    lastHeartbeatMillis = millis();
  }

  int buttonMaterialTubeNum = digitalRead(D0);//material_tube_num
  int buttonStaffNum = digitalRead(D6);//staff_num
  int buttonMaterialSwabNum = digitalRead(D7);//material_swab_num
  int buttonMaterialAlcoholNnum = digitalRead(D8);//material_alcohol_num

  if (buttonMaterialTubeNum == HIGH) {
    Serial.println("buttonMaterialTubeNum");
    material_tube_num = random(1, 10001);
    sendMessage("material_tube_num");
    delay(1000);
  }

  if (buttonStaffNum == LOW) {
    Serial.println("buttonStaffNum");
    staff_num = random(1, 10001);
    sendMessage("staff_num");
    delay(1000);
  }

  if (buttonMaterialSwabNum == LOW) {
    Serial.println("buttonMaterialSwabNum");
    material_swab_num = random(1, 10001);
    sendMessage("material_swab_num");
    delay(1000);
  }

   if (buttonMaterialAlcoholNnum == HIGH) {
    Serial.println("buttonMaterialAlcoholNnum");
    material_alcohol_num = random(1, 10001);
    sendMessage("material_alcohol_num");
    delay(1000);
  }

}
