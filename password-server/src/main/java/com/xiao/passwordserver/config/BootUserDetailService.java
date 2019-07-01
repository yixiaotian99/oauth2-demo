package com.xiao.passwordserver.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sunjinwei
 * @Date 2019-06-21 00:18
 * @Description
 **/
//@Component
public class BootUserDetailService implements UserDetailsService {

//    @Autowired
//    private IUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        user.setAuthorities(authorities);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        return user;
    }
}
