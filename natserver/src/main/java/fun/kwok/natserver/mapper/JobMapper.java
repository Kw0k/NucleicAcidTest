package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.Job;
import fun.kwok.natserver.entity.Result;
import fun.kwok.natserver.entity.SystemUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface JobMapper {


    @Insert({"insert into job(name,groupId,`status`) values(#{jobname},#{groupid},0)"})
    int addJob(String jobname,Integer groupid);


    @Select({"Select * from job where groupid=#{groupid} AND `status`=0"})
    List<Job> getJob(SystemUser systemUser);

    @Select({"<script>"+"Select * from job" +
            "<where>"
            +"<if test='groupid!=null'> groupid=#{groupid} </if>"
            +"<if test='status!=null'>AND `status`=#{status} </if>"+
            "</where></script>"})
    List<Job> getAllJobByGroupId(Integer groupid,Integer status);


    @Select({"<script>Select `status` from job where id=#{jobid}"
            +"<if test='groupid!=null'>AND groupid=#{groupid}</if></script>"
    })
    int getJobStatus(Integer groupid,Integer jobid);

    @Select({"<script>SELECT job.id `jobid`,job.`name` `jobname`,`status`,systemUser.username `group` FROM job LEFT JOIN systemUser ON systemUser.id=job.groupId"
            +"<where>"
            +"<if test='groupid!=null'> job.groupid=#{groupid} </if>"
            +"<if test='status!=null'> AND `status`=#{status} </if>"+
            "</where></script>"
    })
    List<Result> getJobData(Integer groupid,Integer status);

    @Update({"<script>UPDATE job set `status`=1 where id=#{jobid}"
            +"<if test='groupid!=null'>AND groupid=#{groupid}</if></script>"
    })
    int finishjob(Integer jobid,Integer groupid);
}
