package fun.kwok.natserver.controller;

import fun.kwok.natserver.entity.NodeLog;
import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.mapper.NodeLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 硬件节点接口
 */
@Controller
public class NodeControl {

    @Autowired
    NodeLogMapper nodeLogMapper;

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
}
