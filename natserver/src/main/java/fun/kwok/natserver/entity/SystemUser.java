package fun.kwok.natserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SystemUser {
    private Integer id;
    private String username;
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastlogintime;
    private Integer role; // 0超级管理员 1单位账号 2采集人员  采集人员不能登录后台 只能登录采集端
    private Integer groupid;
    private String group; // 所属单位名称

    public SystemUser() {
    }

    public SystemUser(Integer id, String username, String password, Date lastlogintime, Integer role, Integer groupid) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastlogintime = lastlogintime;
        this.role = role;
        this.groupid=groupid;
    }
}
