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

    /**
     * add like num
     * @param likedUsername likedUsername
     * @return is added
     */
    @Update("UPDATE mlab.user \n" +
            "SET \n" +
            "    `user`.like_num = `user`.like_num + 1\n" +
            "WHERE\n" +
            "    `user`.username =#{likedUsername};\n")
    boolean addLikeNum(@Param("likedUsername") String likedUsername);

    /**
     * reduce like num
     * @param likedUsername likedUsername
     * @return is reduced
     */
    @Update("UPDATE mlab.user \n" +
            "SET \n" +
            "    `user`.like_num = `user`.like_num - 1\n" +
            "WHERE\n" +
            "    `user`.username =#{likedUsername};\n")
    boolean reduceLikeNum(@Param("likedUsername") String likedUsername);



}
