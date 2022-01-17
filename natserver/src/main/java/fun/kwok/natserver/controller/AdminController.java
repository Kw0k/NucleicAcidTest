package fun.kwok.natserver.controller;


import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.service.*;
import fun.kwok.natserver.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    ResultService resultService;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    JobService jobService;

    @Autowired
    TubeService tubeService;

    @Autowired
    TubeUserService tubeUserService;

    @Autowired
    UserInfoService userInfoService;

    @ResponseBody
    @PostMapping("/admin/getGroup")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getGroup(HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()==0)
            return new ResultInfo(true,200,"success",systemUserService.getGroup());
        else if (systemUser.getRole()==1) {
            List<SystemUser> systemUserList = new ArrayList<SystemUser>();
            systemUserList.add(systemUser);
            return new ResultInfo(true, 200, "success", systemUserList);
        }
        return new ResultInfo(false,null,"未知错误",null);
    }

    @ResponseBody
    @PostMapping("/admin/getJob")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getJob(@RequestParam(value="groupid",required=false) Integer groupid,
                             @RequestParam(value="status",required=false) Integer status,
                             HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()==0)
            return new ResultInfo(true,200,"success",jobService.getAllJobByGroupId(groupid,status));
        else if (systemUser.getRole()==1){
            return new ResultInfo(true,200,"success",jobService.getAllJobByGroupId(systemUser.getId(),status));
        }
        return new ResultInfo(false,null,"未知错误",null);
    }


    @ResponseBody
    @PostMapping("/admin/getJobData") //获取任务分页列表
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getJobData(
                            @RequestParam("pageNum") Integer pageNum,
                            @RequestParam("pageSize") Integer pageSize,
                            @RequestParam(value="groupid",required=false) Integer groupid,
                            @RequestParam(value="status",required=false) Integer status,
                             HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()==0)
            return new ResultInfo(true,200,"success",jobService.getJobData(pageNum,pageSize,groupid,status));
        else if (systemUser.getRole()==1){
            return new ResultInfo(true,200,"success",jobService.getJobData(pageNum,pageSize,systemUser.getId(),status));
        }
        return new ResultInfo(false,null,"未知错误",null);
    }


    @ResponseBody
    @GetMapping("/admin/getTubeCount")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getTubeCount(HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        Integer groupid;
        if (systemUser.getRole()==0)
            groupid=null;
        else
            groupid=systemUser.getId();
        int[] result=new int[3];
        result[0]=tubeService.countTube(groupid);// 已采集试管
        result[1]=tubeService.countTubeStatus12(groupid);//已检测试管
        result[2]=tubeService.countTubeStatus1(groupid);//阴性试管
        return new ResultInfo(true,200,"success",result);
    }
    @ResponseBody
    @GetMapping("/admin/getTubeUserCount")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultInfo getTubeUserCount( ){
        int[] result=new int[3];
        result[0]=tubeUserService.countTubeUser();// 已采集人次
        result[1]=tubeUserService.countTubeUser12();//已检测人次
        result[2]=tubeUserService.countTubeUser1();//阴性人次
        return new ResultInfo(true,200,"success",result);
    }

    @ResponseBody
    @GetMapping("/admin/getUserInfoCount")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultInfo getUserInfoCount( ){
        return new ResultInfo(true,200,"success",userInfoService.userInfoCount());
    }

    @ResponseBody
    @PostMapping("/admin/addJob")//添加任务
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo addJob(
            @RequestParam("jobname") String jobname,
            @RequestParam("groupid") Integer groupid,
            HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        int flag=0;
        if (systemUser.getRole()==0)
            flag=jobService.addJob(jobname,groupid);
        else
            flag=jobService.addJob(jobname,systemUser.getId());
        if (flag>0)
            return new ResultInfo(true,null,"添加成功",null);
        else
            return new ResultInfo(false,null,"添加失败",null);
    }
    @ResponseBody
    @PostMapping("/admin/finishJob")//添加任务
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo finishJob(
            @RequestParam("jobid") Integer jobid,
            HttpSession session){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        int flag=0;
        if (systemUser.getRole()==0)
            flag=jobService.finishjob(jobid,null);
        else
            flag=jobService.finishjob(jobid,systemUser.getId());
        if (flag>0)
            return new ResultInfo(true,null,"设置任务状态成功",null);
        else
            return new ResultInfo(false,null,"设置任务状态失败",null);
    }

    @ResponseBody
    @PostMapping("/admin/getResult")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultInfo getResult(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize,
                                @RequestParam(value="tubeid",required=false) String tubeid,
                                @RequestParam(value="idcardnum",required=false) String idcardnum,
                                @RequestParam(value="status",required=false) Integer status,
                                @RequestParam(value="operatorid",required=false) Integer operatorid,
                                @RequestParam(value="jobid",required=false) Integer jobid,
                                @RequestParam(value="groupid",required=false) Integer groupid,
                                @RequestParam(value="time1",required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date time1,
                                @RequestParam(value="time2",required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date time2
                                ){
        if ("".equals(idcardnum))
            idcardnum=null;
        if ("".equals(tubeid))
            tubeid=null;
        return new ResultInfo(true,200,"success",
                resultService.getAllResult(pageNum,pageSize,idcardnum,tubeid,status,operatorid
                        ,jobid,groupid,time1,time2));
    }



    @ResponseBody
    @PostMapping("/admin/getAllUserInfo") //人员信息分页列表
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultInfo getAllUserInfo(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value="idcardnum",required=false) String idcardnum
                                     ){
        if ("".equals(idcardnum))
            idcardnum=null;
        return new ResultInfo(true,200,"success",userInfoService.getAllUserInfo(pageNum, pageSize,idcardnum));
    }


    @ResponseBody
    @PostMapping("/admin/getAllSystemUser") //系统用户分页列表
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getAllSystemUser(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value="role",required=false) Integer role,
                                     @RequestParam(value="groupid",required=false) Integer groupid,
                                       HttpSession session
    ){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()==0)
            return new ResultInfo(true,200,"success",systemUserService.getAllSystemUser(pageNum,pageSize,role,groupid));
        else
            return new ResultInfo(true,200,"success",systemUserService.getAllSystemUser(pageNum,pageSize,2,systemUser.getId()));
    }

    @ResponseBody
    @PostMapping("/admin/delSystemUser") //删除系统用户
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo delSystemUser(@RequestParam("id") Integer id,
                                       HttpSession session
    ){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        int flag=0;
        if (systemUser.getRole()==0)
            flag=systemUserService.delSystemUser(id,null);
        else
            flag=systemUserService.delSystemUser(id,systemUser.getId());
        if (flag>0)
            return new ResultInfo(true,null,"删除成功",null);
        else
            return new ResultInfo(false,null,"删除失败",null);
    }

    @ResponseBody
    @PostMapping("/admin/addOrUpdateSystemUser") //添加或修改系统用户
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo addOrUpdateSystemUser(@RequestParam(value = "id",required = false) Integer id,
                                            @RequestParam(value = "username",required = false) String username,
                                            @RequestParam(value = "password",required = false) String password,
                                            @RequestParam(value = "role",required = false) Integer role,
                                            @RequestParam(value = "groupid",required = false) Integer groupid,
                                    HttpSession session
    ){
        if (role==null||role==0)
            return new ResultInfo(false,null,"参数错误",null);
        if (role==2&&groupid==null)
            return new ResultInfo(false,null,"参数错误",null);
        if (password!=null&&!"".equals(password))
            password= MD5Util.MD5Lower(password);
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()!=0){
            role=2;
            groupid=systemUser.getId();
        }
        SystemUser postSystemUser= new SystemUser();
        postSystemUser.setId(id);
        postSystemUser.setPassword(password);
        postSystemUser.setUsername(username);
        postSystemUser.setRole(role);
        postSystemUser.setGroupid(groupid);
        int flag=systemUserService.addOrUpDateSystemUser(postSystemUser);
        if (flag>0)
            return new ResultInfo(true,200,"操作成功",null);
        return new ResultInfo(false,null,"操作失败",null);
    }

    @ResponseBody
    @PostMapping("/admin/setTubeStatus")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo setTubeStatus(HttpSession session,
                                    @RequestParam("tubeid") String tubeid,
                                    @RequestParam("status") Integer status
                                    ){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        int falg=0;
        if (systemUser.getRole()==0)
            falg=tubeService.setTubeStatus(status,tubeid,null);
        if (systemUser.getRole()==1)
            falg=tubeService.setTubeStatus(status,tubeid,systemUser.getId());
        if (falg>0)
            return new ResultInfo(true,200,"发布结果成功",null);
        else
            return new ResultInfo(false,null,"数据库中不存在该试管或无权操作该试管",null);
    }

    @ResponseBody
    @PostMapping("/admin/setJobTubeStatus")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo setJobTubeStatus(HttpSession session,
                                    @RequestParam("jobid") Integer jobid
    ){
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        int falg=0;
        if (systemUser.getRole()==0)
            falg=tubeService.setJobTube(jobid,null);
        if (systemUser.getRole()==1)
            falg=tubeService.setJobTube(jobid,systemUser.getId());
        if (falg>0)
            return new ResultInfo(true,200,"批量发布结果成功",null);
        else
            return new ResultInfo(false,null,"批量发布失败",null);
    }

    @ResponseBody
    @PostMapping("/admin/getTube")
    @PreAuthorize("hasRole('ROLE_GROUP') or hasRole('ROLE_ADMIN')")
    public ResultInfo getTube(HttpSession session,
                                @RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize,
                                @RequestParam(value="tubeid",required=false) String tubeid,
                                @RequestParam(value="status",required=false) Integer status,
                                @RequestParam(value="operatorid",required=false) Integer operatorid,
                                @RequestParam(value="jobid",required=false) Integer jobid,
                                @RequestParam(value="groupid",required=false) Integer groupid,
                                @RequestParam(value="time1",required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date time1,
                                @RequestParam(value="time2",required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date time2
    ){
        if ("".equals(tubeid))
            tubeid=null;
        SystemUser systemUser= (SystemUser) session.getAttribute("user");
        if (systemUser.getRole()==0)
            return new ResultInfo(true,200,"success", tubeService.getTube(pageNum,pageSize,tubeid,status,operatorid,jobid,groupid,time1,time2));
        else if (systemUser.getRole()==1)
            return new ResultInfo(true,200,"success", tubeService.getTube(pageNum,pageSize,tubeid,status,operatorid,jobid,systemUser.getId(),time1,time2));
        return new ResultInfo(false,null,"未知错误",null);
    }
}
