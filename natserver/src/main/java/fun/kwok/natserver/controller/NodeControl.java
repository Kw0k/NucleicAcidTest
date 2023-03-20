package fun.kwok.natserver.controller;

import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.service.SystemUserService;
import fun.kwok.natserver.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 硬件节点接口
 */
@Controller
public class NodeControl {

    @Autowired
    SystemUserService systemUserService;

    /**
     * 硬件节点注册上线,默认密码123456
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/node/register")
    public ResultInfo nodeRegister(@RequestParam("username") String username,
                                   @RequestParam(value = "password", required = false) String password,
                                   @RequestParam(value = "groupid", required = false) Integer groupid) {
        if (password != null && !"".equals(password))
            password = MD5Util.MD5Lower(password);
        SystemUser systemUser = new SystemUser();
        systemUser.setPassword(password);
        systemUser.setUsername(username);
        systemUser.setGroupid(groupid);
        systemUser.setRole(2);
        int flag = systemUserService.addOrUpDateSystemUser(systemUser);
        if (flag > 0)
            return new ResultInfo(true, 200, "操作成功", null);
        return new ResultInfo(false, null, "操作失败", null);
    }
}
