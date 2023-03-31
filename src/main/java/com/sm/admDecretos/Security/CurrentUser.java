package com.sm.admDecretos.Security;

import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    public CurrentUser(String username, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired, boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private com.sm.admDecretos.Core.Entity.Db.Usuario user;

    public com.sm.admDecretos.Core.Entity.Db.Usuario  getUser() {
        return user;
    }

    public void setUser(com.sm.admDecretos.Core.Entity.Db.Usuario  user) {
        this.user = user;
    }
}