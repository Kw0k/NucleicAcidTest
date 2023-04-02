package fun.kwok.natserver.mapper;

import fun.kwok.natserver.entity.NodeLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface NodeLogMapper {

    @Insert({"insert into node_log(opt_id) values(#{optId})"})
    int addNode(int optId);

    @Select({"select * from node_log where opt_id=#{optID}"})
    NodeLog selectNode4OptId(int optID);

    @Update({"UPDATE node_log SET " +
            "session_id=#{session_id}" +
            "where opt_id=#{opt_id}"})
    int updateNodeLogSessionID(NodeLog nodeLog);

    @Update({"UPDATE node_log SET " +
            "last_time=#{datetime}" +
            "where opt_id=#{optID}"})
    int setNodeLastTime(int optID, Date datetime);

    @Update({"UPDATE node_log set " +
            "session_id=''" +
            "where session_id=#{sessionId}"})
    int setDisconnected(String sessionId);

    @Update({"UPDATE node_log set " +
            "staff_num=#{staff_num} " +
            "where session_id=#{sid}"})
    int setStuffNumBySessionId(String sid, Integer staff_num);

    @Update({"UPDATE node_log set " +
            "material_alcohol_num=#{alcohol_num} " +
            "where session_id=#{sid}"})
    int setMaterialAlcoholNumBySessionId(String sid, Integer alcohol_num);

    @Update({"UPDATE node_log set " +
            "material_swab_num=#{swab_num} " +
            "where session_id=#{sid}"})
    int setMaterialSwabNumBySessionId(String sid, Integer swab_num);

    @Update({"UPDATE node_log set " +
            "material_tube_num=#{tube_num} " +
            "where session_id=#{sid}"})
    int setMaterialTubeNumBySessionId(String sid, Integer tube_num);
}