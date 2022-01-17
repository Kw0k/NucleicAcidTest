package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class ResultInfo {
    private boolean flag;
    private Integer code;
    private String msg;
    private Object data;

    public ResultInfo(boolean flag,Integer code, String msg, Object data) {
        this.flag = flag;
        this.code=code;
        this.msg = msg;
        this.data = data;
    }
}
