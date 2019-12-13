package org.sse.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.community.model.Like;

/**
 * @author HPY
 */
@Component
@Mapper
public interface LikeMapper {

    /**
     * set like status
     * @param status 1 means like,0 means dislike
     * @param like_id like_id
     * @return is set
     */
    @Update("UPDATE `like` \n" +
            "SET \n" +
            "    `status` = #{status}\n" +
            "WHERE\n" +
            "    like_id=#{like_id}")
    boolean setStatus(@Param("status") long status, @Param("like_id") long like_id);

    /**
     * get Like by username and comment/post
     * @param username username
     * @param type 0 means post ,1 means comment
     * @param id post or comment Id
     * @return like
     */
    @Select("SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    `like`\n" +
            "WHERE\n" +
            "    username = #{username} AND `type` = #{type}\n" +
            "        AND type_id = #{id}\n" +
            "LIMIT 1")
    @Results(value = {
            @Result(property = "likeId",column = "like_id"),
            @Result(property = "typeId",column = "type_id"),
            @Result(property = "createTime",column = "create_time")
    })
    Like getLikeByNameAndType(@Param("username") String username,
                              @Param("type") long type,
                              @Param("id") long id);

    /**
     * insert like into Like with parameters as follows
     * @param typeId typeId
     * @param type 0 means post, 1 means comment
     * @param username username
     * @param status 1 means like, 0 means unlike
     * @return is inserted
     */
    @Insert("INSERT `like` (type_id,`type`,username,status,create_time)\n" +
            "VALUES (#{typeId},#{type},#{username},#{status},now())")
    boolean insertIntoLike(@Param("typeId") long typeId,
                       @Param("type") long type,
                       @Param("username")String username,
                       @Param("status") int status);

    /**
     * set status = 1 amd update createTime
     * @param likeId like id
     * @return is set
     */
    @Update("UPDATE mlab.`like` \n" +
            "SET \n" +
            "    `like`.`status` = 1,\n" +
            "    `like`.`create_time` = NOW()\n" +
            "WHERE\n" +
            "    like_id = #{likeId};\n"
           )
    boolean setStatusAndCreateTime(@Param("likeId") long likeId);

    /**
     * set status =0 by specific comment/post
     * @param type 0 means post,1 means comment
     * @param typeId post/comment id
     * @return is set
     */
    @Update("UPDATE `like` \n" +
            "SET \n" +
            "    `like`.status = 0\n" +
            "WHERE\n" +
            "    `like`.type = #{type} AND `like`.type_id = #{type_id}")
    boolean setStatusByTypeAndTypeId(@Param("type") long type,@Param("type_id")long typeId);
}
