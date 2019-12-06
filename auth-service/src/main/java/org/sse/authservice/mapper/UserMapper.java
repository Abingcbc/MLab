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
     * create new user
     * @param username username
     * @param password password
     * @param email email
     * @return number of affected rows
     */
    @Insert("insert into USER values(#{username}, #{password}, #{email});")
    int createNewUser(@Param("username") String username,
                      @Param("password") String password,
                      @Param("email") String email);

    /**
     * get user auth info by username when login
     * @param username user's name
     * @return user auth info
     */
    @Select("select username, password from USER where username = #{username}")
    UserAuthInfo getUserAuthInfoByUsername(@Param("username") String username);

    /**
     * update user's password
     * @param username new username
     * @param password new password
     * @param email new email
     * @return number of affected rows
     */
    @Update("update user\n" +
            "set password = #{password}, email = #{email}\n" +
            "where username = #{username}\n")
    int updateInfo(@Param("username") String username,
                   @Param("password") String password,
                   @Param("email") String email);

    /**
     * update user's password
     * @param username new username
     * @param email new email
     * @return number of affected rows
     */
    @Update("update user\n" +
            "set email = #{email}\n" +
            "where username = #{username}\n")
    int updateEmail(@Param("username") String username,
                       @Param("email") String email);
}
