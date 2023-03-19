package fun.kwok.natserver.service;

import fun.kwok.natserver.entity.*;
import fun.kwok.natserver.mapper.SystemV2UserMapper;
import fun.kwok.natserver.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SystemV2UserService {

    @Autowired
    SystemV2UserMapper systemV2UserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;


    public ResultInfo systemV2CheckLogin(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return new ResultInfo(false, 408, "没有登录", null);
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("systemV2")) {
                String value = cookie.getValue();
                String[] split = value.split("-");
                SystemV2User systemV2User = this.systemV2UserMapper.getSystemV2User(split[0]);
                if (systemV2User != null && systemV2User.getPassword().equals(split[1])) {
                    return new ResultInfo(true, 200, "success", null);
                }
            }
        }
        return new ResultInfo(false, 408, "没有登录", null);
    }


    public ResultInfo systemV2LoginOrRegister(HttpSession session, HttpServletResponse response, String username, String password) {
        //执行登录和注册相关逻辑
        SystemV2User systemV2User = this.systemV2UserMapper.getSystemV2User(username);
        if (systemV2User == null) {
            this.systemV2UserMapper.addSystemV2User(new SystemV2User(username, password));
            Cookie systemV2 = new Cookie("systemV2", username + "-" + password);
            response.addCookie(systemV2);

            return new ResultInfo(true, 200, "注册并登录成功", null);
        } else {
            //判断密码
            if (systemV2User.getPassword().equals(password)) {
                Cookie systemV2 = new Cookie("systemV2", username + "-" + password);
                systemV2.setSecure(false);
                response.addCookie(systemV2);
                return new ResultInfo(true, 200, systemV2User.getUsername() + "登录成功", null);
            } else {
                return new ResultInfo(false, 410, "密码错误", null);
            }
        }
    }

    /**
     * 添加或升级用户信息
     *
     * @param request
     * @param userInfo
     * @return
     */
    public ResultInfo addOrUpdateUserInfo(HttpServletRequest request, UserInfo userInfo) {
        String username = null;
        String password = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("systemV2")) {
                String[] split = cookie.getValue().split("-");
                username = split[0];
                password = split[1];
            }
        }
        if (username == null || password == null) {
            return new ResultInfo(false, 408, "没有登录", null);
        }
        //对比账号密码
        SystemV2User systemV2User = this.systemV2UserMapper.getSystemV2User(username);
        if (systemV2User == null) {
            return new ResultInfo(false, 409, "尚未注册", null);
        }
        if (systemV2User.getPassword().equals(password)) {
            //身份验证通过，可以进行信息录入了
            List<UserInfo> list = userInfoMapper.getUserInfoByIdCardNum(userInfo);
            boolean flag = true;
            //判断并在record表中添加当前一条关联语句
            List<SystemV2UserRecord> userV2Record = systemV2UserMapper.getUserV2Record(username, userInfo.getIdcardnum());
            System.out.println("记录为" + userV2Record.toString());
            if (list == null || list.size() == 0) {
                systemV2UserMapper.addUserV2Record(username, userInfo.getIdcardnum());
            }
            if (list != null && list.size() > 0) {
                if (userInfoMapper.updateUserInfo(userInfo) == 0)
                    flag = false;
            } else {
                if (userInfoMapper.addUserInfo(userInfo) == 0)
                    flag = false;
            }
            if (flag) {
                return new ResultInfo(true, null, "提交成功", null);
            }
        }
        return new ResultInfo(false, 410, "不可预知错误", null);
    }


    /**
     * 通过身份证查找用户
     *
     * @param idcardnum
     * @param request
     * @return
     */
    public ResultInfo getUserInfoBySystemV2AndIdCardNum(String idcardnum, HttpServletRequest request) {
        String username = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("systemV2")) {
                username = cookie.getValue().split("-")[0];
                break;
            }
        }
        if (username == null) {
            return new ResultInfo(false, 408, "无法获取用户名", null);
        }
        if (idcardnum == null || "".equals(idcardnum))
            return new ResultInfo(false, 404, "idcardnum参数错误", null);
        List<UserInfo> list = userInfoMapper.getUserInfoBySystemV2AndIdCardNum(idcardnum, username);
        if (list != null && list.size() > 0) {
            return new ResultInfo(true, null, "success", list);
        } else {
            return new ResultInfo(false, 404, "UserInfo查询不到", list);
        }
    }

    public ResultInfo getUserInfoByName(HttpServletRequest request) {
        String username = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("systemV2")) {
                String[] split = cookie.getValue().split("-");
                username = split[0];
                break;
            }
        }
        if (username == null) {
            return new ResultInfo(false, 404, "没有登录啊我去", null);
        }
        List<UserInfo> userInfoBySystemV2Name = userInfoMapper.getUserInfoBySystemV2Name(username);
        return new ResultInfo(true, null, "success", userInfoBySystemV2Name);

    }

    public ResultInfo deleteBindingRelationshipByV2SystemAndUserInfo(String idcardnum, HttpServletRequest request) {
        String username = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("systemV2")) {
                String[] split = cookie.getValue().split("-");
                username = split[0];
                break;
            }
        }
        if (username == null) {
            return new ResultInfo(false, 404, "没有登录啊我去", null);
        }
        int i = systemV2UserMapper.deleteBindingRelationshipByV2SystemAndUserInfo(username, idcardnum);
        if (i > 0) {
            return new ResultInfo(true, null, "删除成功", null);
        } else {
            return new ResultInfo(false, null, "删除失败", null);
        }
    }
}
