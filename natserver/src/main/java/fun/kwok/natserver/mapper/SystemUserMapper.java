package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.SystemUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemUserMapper {

    @Select("select * from systemUser where username=#{username} and password=#{password}")
     SystemUser getSystemUserByLogin(String username, String password);

    @Update("update systemUser set lastlogintime=#{lastlogintime} where id=#{id}")
     int UpdatLastLoginTime(SystemUser systemUser);

    @Select("Select * from systemUser where username=#{username}")
     SystemUser getSystemUserByName(String username);

    @Select("Select username,id FROM systemUser where role=1")
     List<SystemUser> getGroup();

    @Select({"<script>SELECT s1.groupId,s1.id,s1.username,s1.lastlogintime,s1.role,s2.username `group` FROM systemUser s1 LEFT JOIN systemUser s2 on s1.groupid =s2.id"
    +"<where>"
            +"<if test='role!=null'> s1.role=#{role} </if>"
            +"<if test='groupid!=null'>AND s1.groupId=#{groupid} </if>"+
            "</where></script>"
    })
     List<SystemUser> getAllSystemUser(Integer role,Integer groupid);

    @Delete({"<script>DELETE FROM systemUser where id=#{id}"
            +"<if test='groupid!=null'>AND groupId=#{groupid}</if></script>"
    })
    int delSystemUser(Integer id,Integer groupid);


    @Update("<script>"+"update systemUser set username=#{username},groupid=#{groupid}," +
            "<if test='password!=null'>password=#{password},</if>"+
            "role=#{role} where id=#{id}"
            +"</script>")
     int updaetUser(SystemUser systemUser);
    @Insert("INSERT INTO systemUser(username,password,role,groupid) VALUES(#{username},#{password},#{role},#{groupid})")
     int addUser(SystemUser systemUser);
}
