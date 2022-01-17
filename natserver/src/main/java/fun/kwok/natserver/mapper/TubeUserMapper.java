package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TubeUserMapper {

    @Insert({
            "<script>",
            "insert into tubeUser(tubeId, idcardnum) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{tubeId}, #{item.idcardnum})",
            "</foreach>",
            "</script>"
    })
    int addTubeUser(List<UserInfo> list,String tubeId);




    //已采集人次
    @Select("Select count(*) from tubeUser")
    int countTubeUser();

    @Select("SELECT COUNT(*) FROM tubeUser LEFT JOIN tube ON tubeUser.tubeId=tube.tubeId WHERE tube.`status`!=0")
    int countTubeUser12();

    @Select("SELECT COUNT(*) FROM tubeUser LEFT JOIN tube ON tubeUser.tubeId=tube.tubeId WHERE tube.`status`=1")
    int countTubeUser1();

}
