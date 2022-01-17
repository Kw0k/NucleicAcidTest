package fun.kwok.natserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Tube  {
    private String tubeid;
    private Integer operatorid;
    private Integer groupid;
    private Integer jobid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date starttime;//采集时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endtime;//检测时间
    private Integer status;

}