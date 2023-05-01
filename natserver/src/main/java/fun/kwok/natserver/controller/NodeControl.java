package fun.kwok.natserver.controller;

import fun.kwok.natserver.entity.NodeLog;
import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.mapper.NodeLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * 硬件节点接口
 */
@Controller
public class NodeControl {

    @Autowired
    NodeLogMapper nodeLogMapper;


    @Autowired
    private NodeWebSocketHandler nodeWebSocketHandler;

    /**
     * 获得所有的硬件节点当前信息
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/node/get_all")
    public ResultInfo nodeAllInfo() {
        List<NodeLog> nodeLogs = nodeLogMapper.getAll();
        return new ResultInfo(true, null, "获取成功", nodeLogs);
    }

    /**
     * 下发节点操作
     */
    @ResponseBody
    @PostMapping("/node/opt")
    public ResultInfo nodeOpt(@RequestParam("optId") int optId, @RequestParam("instruction") int instruction) throws IOException {
        nodeWebSocketHandler.sendInstructionByNodeId(optId, instruction);
        return new ResultInfo(true, null, "发送指令成功", null);
    }
}
