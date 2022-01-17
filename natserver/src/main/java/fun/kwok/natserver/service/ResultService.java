package fun.kwok.natserver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.mapper.ResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResultService {
    @Autowired
    ResultMapper resultMapper;

    public PageInfo<Result> getAllResult(int pageNum, int pageSize, String idcardnum, String tubeid, Integer status, Integer operatorid, Integer jobid, Integer groupid, Date time1, Date time2){
        PageHelper.startPage(pageNum,pageSize,true);
        List<Result> resultList=resultMapper.getResult(idcardnum,tubeid,  status, operatorid, jobid, groupid, time1, time2);
        return new PageInfo<Result>(resultList);
    }

    public List<Result> getResultByTnameAndIdCardNum(String idcardnum,String tname){
        return resultMapper.getResultByTnameAndIdCardNum(idcardnum,tname);
    }
}
