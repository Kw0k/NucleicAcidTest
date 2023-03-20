package fun.kwok.natserver.controller;


import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.entity.WechatUser;
import fun.kwok.natserver.service.UserInfoService;
import fun.kwok.natserver.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WechatUserController {
    @Autowired
    WechatUserService wechatUserService;

    @Autowired
    UserInfoService userInfoService;

    @ResponseBody
    @PostMapping("/wechat/delWechatUser") //删除微信openid与个人信息间的绑定
    public ResultInfo delWechatUser(HttpSession session, @RequestParam(value = "idcardnum", required = false) String idcardnum) {
        String openid = (String) session.getAttribute("openid");
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        if (idcardnum == null || "".equals(idcardnum))
            return new ResultInfo(false, 404, "参数错误", null);
        if (wechatUserService.delWechatUser(new WechatUser(openid, idcardnum)) > 0)
            return new ResultInfo(true, null, "删除成功", null);
        return new ResultInfo(false, null, "删除失败", null);
    }

    @ResponseBody
    @PostMapping("/wechat/userInfoAll") //通过openid获取openid绑定的所有用户信息
    public ResultInfo getUserInfoByOpenId(HttpSession session) {
        String openid = (String) session.getAttribute("openid");
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenid(openid);
        return new ResultInfo(true, null, "success", userInfoService.getUserInfoByOpenId(wechatUser));
    }

    @ResponseBody
    @PostMapping("/wechat/userInfoViewAndEdit")
    public ResultInfo getUserInfoByOpenIdAndIdCardNum(@RequestParam(value = "idcardnum", required = false) String idcardnum, HttpSession session) {
        String openid = (String) session.getAttribute("openid");
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        if (idcardnum == null || "".equals(idcardnum))
            return new ResultInfo(false, 404, "参数错误", null);
        List list = userInfoService.getUserInfoByOpenIdAndIdCardNum(new WechatUser(openid, idcardnum));
        if (list != null && list.size() > 0) {
            return new ResultInfo(true, null, "success", list);
        } else {
            return new ResultInfo(false, 404, "参数错误", list);
        }
    }

    @ResponseBody
    @PostMapping("/wechat/userInfo")
    public ResultInfo addOrUpdateUserInfo(HttpSession session,
                                          @RequestParam("idcardnum") String idcardnum,
                                          @RequestParam("tname") String tname,
                                          @RequestParam("sex") Integer sex,
                                          @RequestParam("phonenum") String phonenum,
                                          @RequestParam("area") String area,
                                          @RequestParam("address") String address) {
        String openid = (String) session.getAttribute("openid");
        if (openid == null || "".equals(openid))
            return new ResultInfo(false, 408, "请先登录", null);
        if (wechatUserService.addOrUpdateUserInfo(new WechatUser(openid, idcardnum), new UserInfo(idcardnum, tname, sex, phonenum, area, address)))
            return new ResultInfo(true, null, "提交成功", null);
        else
            return new ResultInfo(false, 500, "服务器异常", null);
    }
}
