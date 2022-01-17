package fun.kwok.natserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Result {
    private String idcardnum;
    private String tname;
    private Integer sex;
    private String phonenum;
    private String area;
    private String address;
    private String group;
    private String operator;
    private String jobname;
    private String tubeId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date starttime;//采集时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endtime;//检测时间
    private Integer status;
    private Integer jobid;

}
