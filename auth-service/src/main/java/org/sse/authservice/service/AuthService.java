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
@Transactional(rollbackFor = Exception.class)
public class AuthService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public int register(String username, String password, String email) {
        if (userMapper.getUserAuthInfoByUsername(username) != null) {
            return 0;
        } else {
            password = passwordEncoder.encode(password);
            if (userMapper.createNewUser(username, password, email) == 1) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * update user's information
     * @param username new username
     * @param oPassword origin password
     * @param password new password
     * @param email email
     * @return -1: No such user
     *         -2: Update email failed
     *         -3: Update password or email failed
     *         -4: origin password wrong
     *         1: success
     */
    public int updateInfo(String username,
                          String oPassword,
                          String password,
                          String email) {
        if (userMapper.getUserAuthInfoByUsername(username) == null) {
            return -1;
        }
        // no password update
        if (oPassword == null) {
            if (userMapper.updateEmail(username, email) == 1) {
                return 1;
            } else {
                return -2;
            }
        } else {
            if (userMapper.getUserAuthInfoByUsername(username).getPassword()
                    .equals(passwordEncoder.encode(oPassword))) {
                if (userMapper.updateInfo(username,
                        passwordEncoder.encode(password), email) == 1) {
                    return 1;
                } else {
                    return -3;
                }
            } else {
                return -4;
            }
        }
    }

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
