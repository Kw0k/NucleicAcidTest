package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class SystemV2UserRecord {
    private Integer id;
    private String user_name;
    private String idcardnum;

    public SystemV2UserRecord(Integer id, String user_name, String idcardnum) {
        this.id = id;
        this.user_name = user_name;
        this.idcardnum = idcardnum;
    }

    public SystemV2UserRecord() {
    }
}
