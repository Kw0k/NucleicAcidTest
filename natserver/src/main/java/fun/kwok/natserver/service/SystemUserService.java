package fun.kwok.natserver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.entity.SystemUser;
import fun.kwok.natserver.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserService {

    @Autowired
    SystemUserMapper systemUserMapper;
    public SystemUser getSystemUserByLogin(String username,String password){
        return systemUserMapper.getSystemUserByLogin(username,password);
    }

    public int UpdatLastLoginTime(SystemUser systemUser){
        return systemUserMapper.UpdatLastLoginTime(systemUser);
    }

    public SystemUser getSystemUserByName(String username){
        return systemUserMapper.getSystemUserByName(username);
    }

    public List<SystemUser> getGroup(){
        return systemUserMapper.getGroup();
    }


    public PageInfo<SystemUser> getAllSystemUser(int pageNum, int pageSize,Integer role,Integer groupid){
        PageHelper.startPage(pageNum,pageSize,true);
        List<SystemUser> systemUserList=systemUserMapper.getAllSystemUser(role, groupid);
        return new PageInfo<SystemUser>(systemUserList);
    }

    public int delSystemUser(Integer id,Integer groupid){
        return systemUserMapper.delSystemUser(id, groupid);
    }

    public int addOrUpDateSystemUser(SystemUser systemUser){
        if (systemUser.getId()!=null)
            return systemUserMapper.updaetUser(systemUser);
        else
            return systemUserMapper.addUser(systemUser);
    }
}
