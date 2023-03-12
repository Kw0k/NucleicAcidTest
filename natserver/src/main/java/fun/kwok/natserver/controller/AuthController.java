package fun.kwok.natserver.controller;

import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.service.AuthService;
import fun.kwok.natserver.service.SystemUserService;
import fun.kwok.natserver.service.SystemV2UserService;
import fun.kwok.natserver.service.WechatUserService;
import fun.kwok.natserver.utils.CheckCodeUtil;
import fun.kwok.natserver.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


@Controller
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    WechatUserService wechatUserService;
    @Autowired
    SystemV2UserService systemV2UserService;
    @Autowired
    AuthService authService;

    @GetMapping("/CheckCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            CheckCodeUtil.code(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/operator/checklogin")
    @PreAuthorize("hasRole('ROLE_OPERATOR')") //采集端检测是否已登陆
    public ResultInfo operatorCheckLogin() {
        return new ResultInfo(true, 200, "已登陆", null);
    }

    @ResponseBody
    @PostMapping("/checklogin")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')") //后台检测是否已登陆 管理员和单位账号
    public ResultInfo CheckLogin(HttpSession session) {
        SystemUser systemUser = (SystemUser) session.getAttribute("user");
        return new ResultInfo(true, 200, "已登陆", systemUser);
    }

    @ResponseBody
    @PostMapping("/logout") //采集端和后台 退出登录
    public ResultInfo loginOut(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
        return new ResultInfo(true, 200, "退出成功", null);
    }


    @ResponseBody
    @PostMapping("/system-v2/register-login")//普通用户系统注册
    public ResultInfo registerUser(HttpSession session, HttpServletResponse response, @RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        return systemV2UserService.systemV2LoginOrRegister(session,response, username, password);
    }

    @ResponseBody
    @PostMapping("/system-v2/checklogin")//普通用户系统登录检测
    public ResultInfo checkLogin(HttpServletRequest request) {
        return systemV2UserService.systemV2CheckLogin(request);
    }

    @ResponseBody
    @PostMapping("/wechat/checklogin") //用户端检查是否已登陆
    public ResultInfo weChatCheckLogin(HttpSession session) {
        return authService.weChatCheckLogin(session);
    }


    @ResponseBody
    @PostMapping("/wechat/login") //微信端登录 用户登记个人信息时需要登录
    public ResultInfo weChatLogin(@RequestParam("code") String code, HttpSession session) {
        String openid = (String) session.getAttribute("openid");
        if (openid != null && !"".equals(openid))
            return new ResultInfo(true, 200, "登录成功", null);
        try {
            openid = wechatUserService.getOpenIdByCode(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("openid", openid);
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        return new ResultInfo(true, 200, "登录成功", null);
    }

    @ResponseBody
    @PostMapping("/operator/login")//采集端登录  因为采集端登录没有设置验证码 所以这里和后台登录分开
    public ResultInfo operatorLogin(@RequestParam("username") String username,
                                    @RequestParam("password") String password, HttpSession session) {

        SystemUser systemUser = systemUserService.getSystemUserByLogin(username, MD5Util.MD5Lower(password));
        if (systemUser != null) {
            if (systemUser.getRole() != 2)
                return new ResultInfo(false, 403, "权限不足", null);
            SystemUser temp = systemUser;
            temp.setPassword("");
            session.setAttribute("user", temp);
            systemUser.setLastlogintime(new Date());
            systemUserService.UpdatLastLoginTime(systemUser);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResultInfo(true, null, "登录成功", null);
        } else {
            return new ResultInfo(false, null, "用户名或密码错误", null);
        }
    }

    @ResponseBody
    @PostMapping("/login")//后台登录
    public ResultInfo Login(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("checkcode") String checkcode,
                            HttpSession session) {
        if (session.getAttribute("check_code") != null && checkcode.toUpperCase().equals(session.getAttribute("check_code"))) {
            session.setAttribute("check_code", null);
            SystemUser systemUser = systemUserService.getSystemUserByLogin(username, MD5Util.MD5Lower(password));
            if (systemUser != null) {
                if (systemUser.getRole() == 2)
                    return new ResultInfo(false, 403, "权限不足", null);
                SystemUser temp = systemUser;
                temp.setPassword("");
                session.setAttribute("user", temp);
                systemUser.setLastlogintime(new Date());
                systemUserService.UpdatLastLoginTime(systemUser);
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(username, password);
                Authentication authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return new ResultInfo(true, null, "登录成功", null);
            } else {
                return new ResultInfo(false, null, "用户名或密码错误", null);
            }
        } else {
            return new ResultInfo(false, null, "验证码错误", null);
        }
    }

}
