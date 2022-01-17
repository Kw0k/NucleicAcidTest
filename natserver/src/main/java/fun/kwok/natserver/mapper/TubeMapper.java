package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.entity.Tube;
import fun.kwok.natserver.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface TubeMapper {

    @Select("<script>"+"SELECT s1.username `group`,s2.username `operator`,job.name `jobname`," +
            "tube.tubeId,tube.startTime,tube.endTime,tube.`status` FROM tube " +
            "LEFT JOIN systemUser s1 ON s1.id=tube.groupId " +
            "LEFT JOIN systemUser s2 ON s2.id=tube.operatorId " +
            "LEFT JOIN job ON job.id=tube.jobId"+
            "<where>"
            +"<if test='tubeid!=null'> and tube.tubeId=#{tubeid} </if>"
            +"<if test='operatorid!=null'> and tube.operatorid=#{operatorid} </if>"
            +"<if test='jobid!=null'> and tube.jobid=#{jobid} </if>"
            +"<if test='groupid!=null'> and tube.groupid=#{groupid} </if>"
            +"<if test='status!=null'> and tube.`status`=#{status} </if>"
            +"<if test='time1!=null and time2!=null'> and tube.startTime between #{time1} and #{time2} </if>"
            +"</where></script>")
    List<Result> getTube(String tubeid, Integer status, Integer operatorid, Integer jobid, Integer groupid, Date time1, Date time2);


    @Insert({"insert into tube(tubeId,operatorId,groupId,jobId,startTime,status) values(#{tubeid},#{operatorid},#{groupid},#{jobid},#{starttime},#{status})"})
    int addTube(Tube tube);


    @Update({"<script>UPDATE tube SET `status`=#{status},endTime=#{endTime} WHERE tubeid=#{tubeid}"
            +"<if test='groupid!=null'> AND groupid=#{groupid}</if></script>"
    })
    int setTubeStatus(Integer status,String tubeid,Integer groupid,Date endTime);

    @Update({"<script>UPDATE tube SET `status`=1,endTime=#{endTime} WHERE jobid=#{jobid} AND `status`=0"
            +"<if test='groupid!=null'> AND groupid=#{groupid}</if></script>"
    })
    int setJobTube(Integer jobid,Integer groupid,Date endTime);



    //试管总数
    @Select({"<script>Select count(*) from tube"
            +"<if test='groupid!=null'> where groupid=#{groupid}</if></script>"
    })
    int countTube(Integer groupid);

    //已检测试管数
    @Select({"<script>Select count(*) from tube where `status`!=0"
            +"<if test='groupid!=null'> and groupid=#{groupid}</if></script>"
    })
    int countTubeStatus12(Integer groupid);

    //阴性试管数
    @Select({"<script>Select count(*) from tube where `status`=1"
            +"<if test='groupid!=null'> and groupid=#{groupid}</if></script>"
    })
    int countTubeStatus1(Integer groupid);
}
