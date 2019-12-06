package org.sse.authservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sse.authservice.mapper.UserMapper;
import org.sse.authservice.model.UserAuthInfo;

import java.util.ArrayList;

/**
 * @author cbc
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAuthInfo info = userMapper.getUserAuthInfoByUsername(s);
        if (info == null) {
            throw new UsernameNotFoundException(s);
        } else {
            return info;
        }
    }
}
