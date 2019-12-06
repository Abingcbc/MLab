package org.sser.userservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sser.userservice.mapper.UserMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    UserMapper userMapper;

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
}
