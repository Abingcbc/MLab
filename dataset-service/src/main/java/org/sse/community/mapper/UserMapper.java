package org.sse.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.sse.community.model.User;

import java.util.List;

/**
 * @author HPY
 */
@Component
@Mapper
public interface UserMapper {

    /**
     * get user by username
     * @param username username
     * @return user
     */
    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);
}
