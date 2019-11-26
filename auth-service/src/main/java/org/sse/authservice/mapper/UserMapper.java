package org.sse.authservice.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.sse.authservice.model.UserAuthInfo;

/**
 * @author cbc
 */
@Component
@Mapper
public interface UserMapper {

    /**
     * create new user
     * @param username username
     * @param password password
     * @return number of affected rows
     */
    @Insert("insert into USER values(1, #{username}, #{password}, 1);")
    int createNewUser(@Param("username") String username,
                      @Param("password") String password);

    /**
     * get user auth info by username when login
     * @param username user's name
     * @return user auth info
     */
    @Select("select username, password from USER where username = #{username}")
    UserAuthInfo getUserAuthInfoByUsername(@Param("username") String username);
}
