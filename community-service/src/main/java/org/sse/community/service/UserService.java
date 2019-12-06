package org.sse.community.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.UserMapper;
import org.sse.community.model.User;

import java.util.List;

/**
 * @author HPY
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * get userInfo by username
     * @param username username
     * @return user
     */
    public User getUserByUsername(String username){

        return userMapper.getUserByUsername(username);
    }

    /**
     * check user if exists by username
     * @param username username
     * @return is exist
     */
    public boolean checkUser(String username){
        if(userMapper.getUserByUsername(username)==null){
            return false;
        }
        return true;
    }


}
