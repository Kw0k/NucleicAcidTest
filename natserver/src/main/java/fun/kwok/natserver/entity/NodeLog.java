package fun.kwok.natserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class NodeLog {
    private Integer id;
    private String session_id;
    private Integer group_id;
    private Integer opt_id;
    private Integer material_tube_num;
    private Integer staff_num;
    private Integer material_swab_num;
    private Integer material_alcohol_num;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date last_time;

    public NodeLog() {
    }

    public NodeLog(Integer id, String session_id, Integer group_id, Integer opt_id, Integer material_tube_num, Integer staff_num, Integer material_swab_num, Integer material_alcohol_num, Date last_time) {
        this.id = id;
        this.session_id = session_id;
        this.group_id = group_id;
        this.opt_id = opt_id;
        this.material_tube_num = material_tube_num;
        this.staff_num = staff_num;
        this.material_swab_num = material_swab_num;
        this.material_alcohol_num = material_alcohol_num;
        this.last_time = last_time;
    }
}
