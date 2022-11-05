package com.tlh.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供用户信息
 */
@Service
public class SpringUserDetailsService implements UserDetailsService {


    //模拟用户信息。（通常从数据里中获取）
    private Map<String, SpringUser> userMap = new HashMap<>();

    {
        SpringUser zs = new SpringUser("zs","123456" , "18665370827", Arrays.asList("ROLE_USER"));
        SpringUser ls = new SpringUser("ls","123456", "18665370828", Arrays.asList("ROLE_USER1"));
        SpringUser ww = new SpringUser("ww","123456", "18665370829", Arrays.asList("ROLE_USER"));
        userMap.put("zs", zs);
        userMap.put("ls", ls);
        userMap.put("ww", ww);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SpringUser user = userMap.get(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "SpringUser '" + username + "' not found");
    }

}