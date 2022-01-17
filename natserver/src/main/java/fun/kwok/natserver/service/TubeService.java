package fun.kwok.natserver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.mapper.TubeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TubeService {
    @Autowired
    TubeMapper tubeMapper;

    @Autowired
    JobService jobService;
    public PageInfo getTube(int pageNum, int pageSize, String tubeid, Integer status, Integer operatorid, Integer jobid, Integer groupid, Date time1, Date time2){
        PageHelper.startPage(pageNum,pageSize,true);
        List<Result> resultList=tubeMapper.getTube(tubeid,status, operatorid, jobid, groupid, time1, time2);
        return new PageInfo<Result>(resultList);
    }


    public int setTubeStatus(Integer status,String tubeid,Integer groupid){ return tubeMapper.setTubeStatus(status, tubeid, groupid,new Date());}

    public int setJobTube(Integer jobid,Integer groupid){
        if (jobService.getJobStatus(groupid,jobid)==1){
            return tubeMapper.setJobTube(jobid,groupid,new Date());
        }else {
            return 0;
        }

    }

    public int countTube(Integer groupid){
        return tubeMapper.countTube(groupid);
    }
    public int countTubeStatus12(Integer groupid){
        return tubeMapper.countTubeStatus12(groupid);
    }
    public int countTubeStatus1(Integer groupid){
        return tubeMapper.countTubeStatus1(groupid);
    }


}
