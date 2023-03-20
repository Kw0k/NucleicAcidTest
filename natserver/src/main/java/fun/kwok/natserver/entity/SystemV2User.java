package fun.kwok.natserver.entity;

import lombok.Data;

@Data
public class SystemV2User {
    private Integer id;
    private String username;
    private String password;

    public SystemV2User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SystemV2User() {
    }
}
