package fun.kwok.natserver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.kwok.natserver.entity.Job;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    JobMapper jobMapper;

    public int addJob(String jobname,Integer groupid){
        return jobMapper.addJob(jobname, groupid);
    }
    public List<Job> getJob(SystemUser systemUser){return jobMapper.getJob(systemUser);}
    public List<Job> getAllJobByGroupId(Integer groupid,Integer status){return  jobMapper.getAllJobByGroupId(groupid,status);}
    public int getJobStatus(Integer groupid,Integer jobid){
        return jobMapper.getJobStatus(groupid,jobid);
    }
    public PageInfo<Result> getJobData(int pageNum, int pageSize,Integer groupid,Integer status){
        PageHelper.startPage(pageNum,pageSize,true);
        List<Result> resultList=jobMapper.getJobData(groupid,status);
        return new PageInfo<Result>(resultList);
    }
    public int finishjob(Integer jobid,Integer groupid){
        return jobMapper.finishjob(jobid, groupid);
    }


}
