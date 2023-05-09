package fun.kwok.natserver.controller;

import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.service.SystemV2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SystemV2UserController {

    @Autowired
    SystemV2UserService systemV2UserService;

    @ResponseBody
    @PostMapping("/system-v2/userInfo")
    public ResultInfo addOrUpdateUserInfo(HttpServletRequest request,
                                          @RequestParam("idcardnum") String idcardnum,
                                          @RequestParam("tname") String tname,
                                          @RequestParam("sex") Integer sex,
                                          @RequestParam("phonenum") String phonenum,
                                          @RequestParam("area") String area,
                                          @RequestParam("address") String address) {
        return systemV2UserService.addOrUpdateUserInfo(request, new UserInfo(idcardnum, tname, sex, phonenum, area, address));
    }

    @ResponseBody
    @PostMapping("/system-v2/userInfoAll") //获取该账号下的所有登记信息
    public ResultInfo getUserInfoByName(HttpServletRequest request) {
        return systemV2UserService.getUserInfoByName(request);
    }

    @ResponseBody
    @PostMapping("/system-v2/userInfoViewAndEdit")
    public ResultInfo getUserInfoBySystemV2AndIdCardNum(@RequestParam(value = "idcardnum", required = false) String idcardnum, HttpServletRequest request) {
        return systemV2UserService.getUserInfoBySystemV2AndIdCardNum(idcardnum, request);
    }

    @ResponseBody
    @PostMapping("/system-v2/deleteBindingRelationshipByV2SystemAndUserInfo")
    public ResultInfo deleteBindingRelationshipByV2SystemAndUserInfo(HttpServletRequest request, @RequestParam(value = "idcardnum", required = false) String idcardnum) {
        return systemV2UserService.deleteBindingRelationshipByV2SystemAndUserInfo(idcardnum, request);
    }

}
