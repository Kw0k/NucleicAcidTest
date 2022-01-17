package fun.kwok.natserver.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.entity.WechatUser;
import fun.kwok.natserver.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public List<UserInfo> getUserInfoByOpenId(WechatUser wechatUser){return userInfoMapper.getUserInfoByOpenId(wechatUser);}

    public List<UserInfo> getUserInfoByOpenIdAndIdCardNum(WechatUser wechatUser){return userInfoMapper.getUserInfoByOpenIdAndIdCardNum(wechatUser);}

    public PageInfo<UserInfo> getAllUserInfo(Integer pageNum,Integer pageSize,String idcardnum){
        PageHelper.startPage(pageNum,pageSize,true);
        List<UserInfo> userInfoList=userInfoMapper.getAllUserInfo(idcardnum);
        return new PageInfo<UserInfo>(userInfoList);
    }

    public int userInfoCount(){
        return userInfoMapper.userInfoCount();
    }
}
