package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.SystemV2User;
import fun.kwok.natserver.entity.SystemV2UserRecord;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.entity.WechatUser;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Mapper
public interface SystemV2UserMapper {

    @Select({"Select * from system_v2_user where username=#{userName}"})
    SystemV2User getSystemV2User(String userName);

    @Insert({"insert into system_v2_user(username,password) values(#{username},#{password})"})
    int addSystemV2User(SystemV2User user);

    @Delete("DELETE FROM system_v2_user WHERE username=#{username} and password=#{password}")
    int delSystemV2User(SystemV2User user);

    @Select({"select * from system_v2_uer_record where user_name=#{v2Name} and idcardnum=#{idcardnum}"})
    List<SystemV2UserRecord> getUserV2Record(String v2Name, String idcardnum);

    @Insert({"insert into system_v2_uer_record(user_name,idcardnum) values(#{v2Name},#{idcardnum})"})
    int addUserV2Record(String v2Name, String idcardnum);

    @Delete("DELETE FROM system_v2_uer_record WHERE user_name=#{username} and idcardnum=#{idcardnum}")
    int deleteBindingRelationshipByV2SystemAndUserInfo(String username, String idcardnum);
}
