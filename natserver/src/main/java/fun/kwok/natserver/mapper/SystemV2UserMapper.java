package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.SystemV2User;
import fun.kwok.natserver.entity.WechatUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemV2UserMapper {

    @Select({"Select * from system_v2_user where username=#{userName}"})
    SystemV2User getSystemV2User(String userName);

    @Insert({"insert into system_v2_user(username,password) values(#{username},#{password})"})
    int addSystemV2User(SystemV2User user);

    @Delete("DELETE FROM system_v2_user WHERE username=#{username} and password=#{password}")
    int delSystemV2User(SystemV2User user);

    @Update({"UPDATE system_v2_user SET idcardnum=#{idcardnum} where username=#{username}"})
    int setSystemV2UserIdcardNum(String username, String idcardnum);
}
