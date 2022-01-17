package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class UserInfo {
    private String idcardnum;//身份证号
    private String tname;//真实姓名
    private Integer sex; //1男 2女
    private String phonenum;//手机号
    private String area;//所属辖区
    private String address;//详细地址


    public UserInfo(String idcardnum, String tname, Integer sex, String phonenum, String area, String address) {
        this.idcardnum = idcardnum;
        this.tname = tname;
        this.sex = sex;
        this.phonenum = phonenum;
        this.area = area;
        this.address = address;
    }
}