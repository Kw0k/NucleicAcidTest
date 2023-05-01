package fun.kwok.natserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.kwok.natserver.entity.NodeLog;
import fun.kwok.natserver.entity.NodeSocketBean;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.service.NodeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NodeWebSocketHandler extends TextWebSocketHandler {
    // 存储所有活动 WebSocket 会话的集合
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    //存储最后心跳时间
    private Map<String, Long> lastHeartbeatTimestamps = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private NodeLogService nodeLogService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            handleSocket(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理消息
     * {"apiPort":"1","nodeLog":{"id":1,"session_id":"123","group_id":1,"opt_id":1,"material_tube_num":1,"staff_num":1,"material_swab_num":1,"material_alcohol_num":1,"last_time":"2023-04-02 19:53:30"}}
     */
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
                //判断id是否在系统用户表中
                List<SystemUser> systemUser4IDAndRole = nodeLogService.getSystemUser4IDAndRole(nodeSocketBean.nodeLog.getOpt_id());
                if (systemUser4IDAndRole.isEmpty()) {
                    System.out.println("该ID并未注册采集者权限");
                    return;
                }
                nodeSocketBean.nodeLog.setSession_id(session.getId());
                NodeLog nodeLogResult = nodeLogService.registerOrOnLineNode(nodeSocketBean.nodeLog);
                //修改最后一次连接时间
                nodeLogService.setLastTimeData(nodeSocketBean.nodeLog.getOpt_id());
                //下发服务器数据给节点
                NodeSocketBean result = new NodeSocketBean();
                result.setApiPort("sync_data");
                result.setNodeLog(nodeLogResult);
                sendMessage(sessions.get(session.getId()), objectMapper.writeValueAsString(result));
                return;
            }
            case ("staff_num"): {
                //修改当前节点人数
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("staff_num操作无key");
                    return;
                }
                nodeLogService.setStaffNum(session.getId(), nodeSocketBean);
                updateNodeData(session, nodeSocketBean);
                return;
            }
            case ("material_tube_num"): {
                //修改当前节点试管数量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_tube_num操作无key");
                    return;
                }
                nodeLogService.setMaterialTubeNum(session.getId(), nodeSocketBean);
                updateNodeData(session, nodeSocketBean);
                return;
            }
            case ("material_swab_num"): {
                //修改当前节点拭子数量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_swab_num操作无key");
                    return;
                }
                nodeLogService.setMaterialSwabNum(session.getId(), nodeSocketBean);
                updateNodeData(session, nodeSocketBean);
                return;
            }
            case ("material_alcohol_num"): {
                //修改当前节点消毒酒精剩余量
                if (!sessions.containsKey(session.getId())) {
                    System.out.println("material_alcohol_num操作无key");
                    return;
                }
                nodeLogService.setMaterialAlcoholNum(session.getId(), nodeSocketBean);
                updateNodeData(session, nodeSocketBean);
                return;
            }
            case ("heartbeat"): {
                lastHeartbeatTimestamps.put(session.getId(), System.currentTimeMillis());
            }
            default: {

            }
        }
    }

    private void updateNodeData(WebSocketSession session, NodeSocketBean nodeSocketBean) throws IOException {
        NodeLog nodeLog = nodeLogService.selectNode4OptId(nodeSocketBean.getNodeLog().getOpt_id());
        NodeSocketBean nsb = new NodeSocketBean();
        nsb.setApiPort("sync_data");
        nsb.setNodeLog(nodeLog);
        sendMessage(sessions.get(session.getId()), objectMapper.writeValueAsString(nsb));
    }


    @Scheduled(fixedRate = 10000) // Check every 10 seconds
    public void checkHeartbeats() {
        long currentTime = System.currentTimeMillis();
        long heartbeatTimeout = 10000; // 10 seconds
        System.out.println("检测心跳");
        for (Iterator<Map.Entry<String, Long>> iterator = lastHeartbeatTimestamps.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Long> entry = iterator.next();
            if (currentTime - entry.getValue() > heartbeatTimeout) {
                iterator.remove();
                try {
                    System.out.println("心跳包检测到断开连接，移除" + entry.getKey());
                    afterConnectionClosed(sessions.get(entry.getKey()), CloseStatus.NO_STATUS_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public void sendInstructionByNodeId(Integer nodeId, Integer instruction) throws IOException {
        String userSession = nodeLogService.getUserSession(nodeId);
        if (sessions.containsKey(userSession)) {
            if (sessions.get(userSession).isOpen()) {
                WebSocketSession session = sessions.get(userSession);
                session.sendMessage(new TextMessage("{\"apiPort\":\"instruction\",\"instruction\":\"" + instruction + "\"}"));
            }
        }
    }
}
