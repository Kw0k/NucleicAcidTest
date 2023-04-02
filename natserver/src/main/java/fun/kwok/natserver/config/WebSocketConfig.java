package fun.kwok.natserver.config;

import fun.kwok.natserver.controller.NodeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public static String NodeEndPoint = "/node-endpoint";

    @Autowired
    private NodeWebSocketHandler nodeWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(nodeWebSocketHandler, NodeEndPoint)
                .setAllowedOrigins("*");
    }
}
