package fun.kwok.natserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.entity.Tube;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.service.JobService;
import fun.kwok.natserver.service.TubeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
public class OperatorController {
    @Autowired
    JobService jobService;
    @Autowired
    TubeUserService tubeUserService;
    @ResponseBody
    @PostMapping ("/operator/job")
    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    public ResultInfo getJob(HttpSession httpSession){
        SystemUser systemUser=(SystemUser) httpSession.getAttribute("user");
        return new ResultInfo(true,200,"sucess",jobService.getJob(systemUser));
    }
    @ResponseBody
    @PostMapping("/operator/submit")
    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    public ResultInfo submit(@RequestParam("jobId") Integer jobId,
                             @RequestParam("tubeId") String tubeId,
                             @RequestParam("list") String list,
                             HttpSession httpSession
                             ){
        SystemUser systemUser=(SystemUser) httpSession.getAttribute("user");
        List<UserInfo> userList= JSON.parseObject(list, new TypeReference<List<UserInfo>>(){
        });
        Tube tube=new Tube();
        tube.setTubeid(tubeId);
        tube.setJobid(jobId);
        tube.setOperatorid(systemUser.getId());
        tube.setGroupid(systemUser.getGroupid());
        tube.setStatus(0);
        tube.setStarttime(new Date());
        try {
            tubeUserService.addTubeAndTubeUser(tube,userList);
        }catch (Exception e){
            return new ResultInfo(false,500,"提交失败:数据库中已存在该试管,请检查试管条码或更换试管。",null);
        }
        return new ResultInfo(true,200,"提交成功",null);
    }
}
