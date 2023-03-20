package fun.kwok.natserver.service;

import fun.kwok.natserver.entity.ResultInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    public ResultInfo weChatCheckLogin(HttpSession session) {
        String openid = (String) session.getAttribute("openid");
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        return new ResultInfo(true, 200, "success", null);
    }


}
