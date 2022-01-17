package fun.kwok.natserver.mapper;


import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.entity.WechatUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Insert({"insert into userInfo(idcardnum,tname,sex,phonenum,area,address) values(#{idcardnum},#{tname},#{sex},#{phonenum},#{area},#{address})"})
    int addUserInfo(UserInfo userInfo);

    @Update({"UPDATE userInfo SET tname=#{tname},sex=#{sex},phonenum=#{phonenum},area=#{area},address=#{address} where idcardnum=#{idcardnum}"})
    int updateUserInfo(UserInfo userInfo);

    @Select({"Select * from userInfo where idcardnum=#{idcardnum}"})
    List<UserInfo> getUserInfoByIdCardNum(UserInfo userInfo);

    @Select({"SELECT * FROM userInfo JOIN wechatUser ON wechatUser.idcardnum= userInfo.idcardnum WHERE wechatUser.openid=#{openid}"})
    List<UserInfo> getUserInfoByOpenId(WechatUser wechatUser);

    @Select({"SELECT * FROM userInfo JOIN wechatUser ON wechatUser.idcardnum= userInfo.idcardnum WHERE wechatUser.openid=#{openid} AND wechatUser.idcardnum=#{idcardnum}"})
    List<UserInfo> getUserInfoByOpenIdAndIdCardNum(WechatUser wechatUser);

    @Select({"<script>SELECT * FROM userInfo"
            +"<where>"
            +"<if test='idcardnum!=null'> idcardnum=#{idcardnum} </if>"+
            "</where></script>"
    })
    List<UserInfo> getAllUserInfo(String idcardnum);



    @Select("Select count(*) from userInfo")
    int userInfoCount();
}
