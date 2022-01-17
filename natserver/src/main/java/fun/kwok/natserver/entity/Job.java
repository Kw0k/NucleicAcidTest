package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class Job {
    private Integer id;
    private String name;
    private Integer groupId; //所属单位id 所属单位在systemUser表
    private Integer status; //任务状态 0表示进行中  1表示已完成 已完成的任务不再出现在采集端的任务列表中
}
