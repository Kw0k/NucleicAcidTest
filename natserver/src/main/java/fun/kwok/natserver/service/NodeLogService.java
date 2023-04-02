package fun.kwok.natserver.service;

import fun.kwok.natserver.entity.NodeLog;
import fun.kwok.natserver.entity.NodeSocketBean;
import fun.kwok.natserver.mapper.NodeLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

@Service
public class NodeLogService {

    @Autowired
    private NodeLogMapper nodeLogMapper;

    public NodeLog registerOrOnLineNode(NodeLog requestNodeLog) throws Exception {
        //查找是否有node
        NodeLog nodeLog = nodeLogMapper.selectNode4OptId(requestNodeLog.getOpt_id());
        if (nodeLog == null) {
            nodeLogMapper.addNode(requestNodeLog.getOpt_id());
            nodeLog = new NodeLog();
            nodeLog.setOpt_id(requestNodeLog.getOpt_id());
        }
        //将socket的session注册到数据库
        nodeLog.setSession_id(requestNodeLog.getSession_id());
        int state = nodeLogMapper.updateNodeLogSessionID(nodeLog);
        if (state > 0) {
            System.out.println("注册节点成功");
            return nodeLog;
        } else {
            System.out.println("注册节点失败");
        }
        return null;
    }

    public void setLastTimeData(int OptID) {
        nodeLogMapper.setNodeLastTime(OptID, new Date());
    }

    public void setDisconnected(String sessionId) {
        try {
            nodeLogMapper.setDisconnected(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStaffNum(String sid, NodeSocketBean nodeSocketBean) {
        Integer staff_num = nodeSocketBean.getNodeLog().getStaff_num();
        nodeLogMapper.setStuffNumBySessionId(sid, staff_num);
    }

    public void setMaterialAlcoholNum(String sid, NodeSocketBean nodeSocketBean) {
        Integer alcohol_num = nodeSocketBean.getNodeLog().getMaterial_alcohol_num();
        nodeLogMapper.setMaterialAlcoholNumBySessionId(sid, alcohol_num);
    }

    public void setMaterialSwabNum(String sid, NodeSocketBean nodeSocketBean) {
        Integer swab_num = nodeSocketBean.getNodeLog().getMaterial_swab_num();
        nodeLogMapper.setMaterialSwabNumBySessionId(sid, swab_num);
    }

    public void setMaterialTubeNum(String sid, NodeSocketBean nodeSocketBean) {
        Integer tube_num = nodeSocketBean.getNodeLog().getMaterial_tube_num();
        nodeLogMapper.setMaterialTubeNumBySessionId(sid, tube_num);
    }
}
