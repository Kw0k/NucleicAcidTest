package fun.kwok.natserver.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.entity.Tube;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.mapper.TubeMapper;
import fun.kwok.natserver.mapper.TubeUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TubeUserService {

    @Autowired
    TubeUserMapper tubeUserMapper;
    @Autowired
    TubeMapper tubeMapper;


    @Transactional
    public void addTubeAndTubeUser(Tube tube, List<UserInfo> userList){
        tubeMapper.addTube(tube);
        tubeUserMapper.addTubeUser(userList,tube.getTubeid());
    }


    public int countTubeUser12(){
        return tubeUserMapper.countTubeUser12();
    }
    public int countTubeUser(){
        return tubeUserMapper.countTubeUser();
    }
    public int countTubeUser1(){
        return tubeUserMapper.countTubeUser1();
    }

}
