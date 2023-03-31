package com.sm.admDecretos.Security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class AuthenticateService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(AuthenticateService.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        Usuario user = userRepository.findFirstByUsernameAndStatus(username,0);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("No existe el usuario con username %s.", username));
        }


        ArrayList<GrantedAuthority> authorities = new ArrayList();
        if(user.getAdmin()==1) {
            authorities = (ArrayList) AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }

        // Create a UserDetails object from the data
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),(ArrayList) authorities);
        return buildUserForAuthentication(userDetails, authorities, user);
        //   return userDetails;
    }

    private UserDetails buildUserForAuthentication(UserDetails user,
                                                   List<GrantedAuthority> authorities, Usuario usuario) {
        String username = user.getUsername();
        String password = user.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        CurrentUser currentUser = new CurrentUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);

        currentUser.setUser(usuario);
        return currentUser;
    }


}

