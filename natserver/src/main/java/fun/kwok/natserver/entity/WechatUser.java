package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class WechatUser {
    private Integer id;
    private String openid;
    private String idcardnum;

    public WechatUser(String openid, String idcardnum) {
        this.openid = openid;
        this.idcardnum = idcardnum;
    }

    public WechatUser() {
    }
}