package com.myp.allknow.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserRole implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String userRole;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }
}
