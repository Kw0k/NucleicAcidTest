package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ResultMapper {

    @Select("<script>"+"SELECT userInfo.*,s1.username `group`,s2.username `operator`,job.name `jobname`," +
            "tube.tubeId,tube.startTime,tube.endTime,tube.`status` FROM userInfo " +
            "JOIN tubeUser ON tubeUser.idcardnum = userInfo.idcardnum " +
            "LEFT JOIN tube on tubeUser.tubeId=tube.tubeId " +
            "LEFT JOIN systemUser s1 ON s1.id=tube.groupId " +
            "LEFT JOIN systemUser s2 ON s2.id=tube.operatorId " +
            "LEFT JOIN job ON job.id=tube.jobId"+
            "<where>"
            +"<if test='idcardnum!=null'> tubeUser.idcardnum=#{idcardnum} </if>"
            +"<if test='tubeid!=null'> and tube.tubeId=#{tubeid} </if>"
            +"<if test='operatorid!=null'> and tube.operatorid=#{operatorid} </if>"
            +"<if test='jobid!=null'> and tube.jobid=#{jobid} </if>"
            +"<if test='groupid!=null'> and tube.groupid=#{groupid} </if>"
            +"<if test='status!=null'> and tube.`status`=#{status} </if>"
            +"<if test='time1!=null and time2!=null'> and tube.startTime between #{time1} and #{time2} </if>"
            +"</where></script>"

    )
    List<Result> getResult(String idcardnum,String tubeid,Integer status, Integer operatorid, Integer jobid, Integer groupid, Date time1,Date time2);

    @Select("SELECT userInfo.tname,userInfo.idcardnum,s1.username `group`," +
            "tube.startTime,tube.endTime,tube.`status` FROM userInfo " +
            "JOIN tubeUser ON tubeUser.idcardnum = userInfo.idcardnum " +
            "LEFT JOIN tube on tubeUser.tubeId=tube.tubeId " +
            "LEFT JOIN systemUser s1 ON s1.id=tube.groupId " +
            "where tubeUser.idcardnum=#{idcardnum} and userInfo.tname=#{tname}")
    List<Result> getResultByTnameAndIdCardNum(String idcardnum,String tname);

}
