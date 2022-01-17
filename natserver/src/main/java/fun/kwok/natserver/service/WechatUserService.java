package fun.kwok.natserver.service;
import com.alibaba.fastjson.JSONObject;
import fun.kwok.natserver.entity.UserInfo;
import fun.kwok.natserver.entity.WechatUser;
import fun.kwok.natserver.mapper.UserInfoMapper;
import fun.kwok.natserver.mapper.WechatUserMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
@Service
public class WechatUserService {
    @Value("${WeChat.appid}")
    private String appid;

    @Value("${WeChat.secret}")
    private String secret;


    @Autowired
    WechatUserMapper wechatUserMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    public int delWechatUser(WechatUser wechatUser){return wechatUserMapper.delWechatUser(wechatUser);}
    public String getOpenIdByCode(String code) throws IOException { //通过code获取openid
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                        "appid=" +appid+
                        "&secret=" +secret+
                        "&code=" +code+
                        "&grant_type=authorization_code")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject=JSONObject.parseObject(response.body().string());
        System.out.println(jsonObject);
        return jsonObject.getString("openid");
    }
    public boolean addOrUpdateUserInfo(WechatUser wechatUser, UserInfo userInfo){
        Boolean flag=true;
            if (wechatUserMapper.getWechatUser(wechatUser).size()==0){
                if (wechatUserMapper.addWechatUser(wechatUser)==0)
                 flag=false;
            }
        List list=userInfoMapper.getUserInfoByIdCardNum(userInfo);
            if (list!=null&&list.size()>0){
                if (userInfoMapper.updateUserInfo(userInfo)==0)
                 flag=false;
            }else {
                if (userInfoMapper.addUserInfo(userInfo)==0)
                 flag=false;
            }
        return flag;
    }
}
