package org.sse.authservice.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.authservice.model.UserAuthInfo;

/**
 * @author cbc
 */
@Component
@Mapper
public interface UserMapper {


    /**
     * get user auth info by username when login
     * @param username user's name
     * @return user auth info
     */
    @Select("select username, password from USER where username = #{username}")
    UserAuthInfo getUserAuthInfoByUsername(@Param("username") String username);

}
