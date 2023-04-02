package fun.kwok.natserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fun.kwok.natserver.entity.NodeLog;
import fun.kwok.natserver.entity.NodeSocketBean;
import fun.kwok.natserver.service.NodeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NodeWebSocketHandler extends TextWebSocketHandler {
    // 存储所有活动 WebSocket 会话的集合
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private NodeLogService nodeLogService;

    /**
     * 处理消息
     * {"apiPort":"1","nodeLog":{"id":1,"session_id":"123","group_id":1,"opt_id":1,"material_tube_num":1,"staff_num":1,"material_swab_num":1,"material_alcohol_num":1,"last_time":"2023-04-02 19:53:30"}}
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            handleSocket(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSocket(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(session.getId() + message.getPayload());
        String payload = message.getPayload();
        NodeSocketBean nodeSocketBean;
        nodeSocketBean = objectMapper.readValue(payload, NodeSocketBean.class);
        //可以进行相关操作了
        switch (nodeSocketBean.apiPort) {
            case ("RoL"): {
                //注册或登陆
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("RoL操作无key");
                    return;
                }
                nodeSocketBean.nodeLog.setSession_id(session.getId());
                nodeLogService.registerOrOnLineNode(nodeSocketBean.nodeLog);
                //修改最后一次连接时间
                nodeLogService.setLastTimeData(nodeSocketBean.nodeLog.getId());
                return;
            }
            case ("staff_num"): {
                //修改当前节点人数
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("staff_num操作无key");
                    return;
                }
                nodeLogService.setStaffNum(session.getId(), nodeSocketBean);
                return;
            }
            case ("material_tube_num"): {
                //修改当前节点试管数量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_tube_num操作无key");
                    return;
                }
                nodeLogService.setMaterialTubeNum(session.getId(), nodeSocketBean);
                return;
            }
            case ("material_swab_num"): {
                //修改当前节点拭子数量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_swab_num操作无key");
                    return;
                }
                nodeLogService.setMaterialSwabNum(session.getId(), nodeSocketBean);
                return;
            }
            case ("material_alcohol_num"): {
                //修改当前节点消毒酒精剩余量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_alcohol_num操作无key");
                    return;
                }
                nodeLogService.setMaterialAlcoholNum(session.getId(), nodeSocketBean);
                return;
            }
            default: {

            }
        }
    }

    /**
     * 断开
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed with session ID: " + session.getId());
        sessions.remove(session.getId());
        nodeLogService.setDisconnected(session.getId());
    }

    /**
     * 连接
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished with session ID: " + session.getId());
        sessions.put(session.getId(), session);
    }

    /**
     * 发送消息
     *
     * @param session
     * @param message
     * @throws IOException
     */
    public void sendMessage(WebSocketSession session, String message) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
