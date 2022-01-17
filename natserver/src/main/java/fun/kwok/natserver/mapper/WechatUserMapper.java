package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.WechatUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WechatUserMapper {

    @Select({"Select * from wechatUser where idcardnum=#{idcardnum} AND openid=#{openid}"})
    List<WechatUser> getWechatUser(WechatUser wechatUser);

    @Insert({"insert into wechatUser(idcardnum,openid) values(#{idcardnum},#{openid})"})
    int addWechatUser(WechatUser wechatUser);

    @Delete("DELETE FROM wechatUser WHERE openid=#{openid} and idcardnum=#{idcardnum}")
    int delWechatUser(WechatUser wechatUser);
}
