package fun.kwok.natserver.service;

import fun.kwok.natserver.entity.SystemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyCustomUserService implements UserDetailsService {
    private final SystemUserService systemUserService;

    public MyCustomUserService(SystemUserService systemUserService){
        this.systemUserService=systemUserService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            SystemUser systemUser=systemUserService.getSystemUserByName(username);
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
            if (systemUser!=null){
                if (systemUser.getRole()==2){
                    GrantedAuthority au = new SimpleGrantedAuthority("ROLE_OPERATOR");
                    list.add(au);
                }
                else if (systemUser.getRole()==0){
                    GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADMIN");
                    list.add(au);
                }else if (systemUser.getRole()==1){
                    GrantedAuthority au = new SimpleGrantedAuthority("ROLE_GROUP");
                    list.add(au);
                }
                return new org.springframework.security.core.userdetails.User(systemUser.getUsername(),systemUser.getPassword(), list);
            }
        return null;
    }
}
