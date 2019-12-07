package org.sse.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.sse.community.model.Post;

import java.util.List;

/**
 * @author HPY
 */
@Component
@Mapper
public interface PostMapper {

    /**
     * insert post into Post
     * @param username username
     * @param title title
     * @param content content
     * @return is inserted
     */
    @Insert("insert into post (username, title, content, create_time, like_num,comment_num,status)\n" +
            "value (#{username},#{title}, #{content},now(),0,0,0)")
    boolean insertIntoPost(@Param("username") String username,@Param("title") String title,@Param("content") String content);

    /**
     * set post status by postId with status
     * @param postId postId
     * @param status is deleted, 0 means not deleted, 1 means deleted
     * @return is executed
     */
    @Update("UPDATE post\n" +
            "SET status=#{status}\n" +
            "where post_id =#{postId}")
    boolean updatePostStatus(@Param("postId") int postId, @Param("status") int status);

    /**
     * get post by postId
     * @param postId postId
     * @return post
     */
    @Select("select * from post where post_id = #{postId}")
    @Results(value = {
            @Result(property = "postId",column = "post_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "likeNum",column = "like_num"),
            @Result(property = "commentNum",column = "comment_num")
    })
    Post getPostByPostId(@Param("postId") long postId);

    /**
     * select status by postId
     * @param postId postId
     * @return 0 means not deleted, 1 means deleted
     */
    @Select("SELECT \n" +
            "    status\n" +
            "FROM\n" +
            "    post\n" +
            "WHERE\n" +
            "    post_id = #{postId}")
    int checkStatus(@Param("postId") long postId);

    /**
     * add like num
     * @param postId post id
     * @return is added
     */
    @Update("UPDATE post \n" +
            "SET \n" +
            "    like_num = like_num + 1\n" +
            "WHERE\n" +
            "    post_id = #{postId}")
    boolean addLikeNum(@Param("postId") long postId);

    /**
     * reduce like num
     * @param postId post Id
     * @return is reduced
     */
    @Update("UPDATE post \n" +
            "SET \n" +
            "    like_num = like_num - 1\n" +
            "WHERE\n" +
            "    post_id = #{postId}")
    boolean reduceLikeNum(@Param("postId") long postId);

    /**
     * add comment_num
     * @param postId postId
     * @return is added
     */
    @Update("UPDATE post \n" +
            "SET \n" +
            "    comment_num = comment_num + 1\n" +
            "WHERE\n" +
            "    post_id = #{postId}")
    boolean addCommentNum(@Param("postId") long postId);

    /**
     * reduce comment num
     * @param postId post id
     * @return is reduced
     */
    @Update("UPDATE post \n" +
            "SET \n" +
            "    comment_num = comment_num - 1\n" +
            "WHERE\n" +
            "    post_id = #{postId}")
    boolean reduceCommentNum(@Param("postId") long postId);

    /**
     * get number of post
     * @return number of post
     */
    @Select("SELECT \n" +
            "    COUNT(*)\n" +
            "FROM\n" +
            "    post\n" +
            "WHERE `status`=0;")
    int selectCount();

    /**
     * select posts order by time(id)
     * @param start the pos of start, which is page number * the number of posts in one page
     * @param number the number of posts in one page
     * @return List of posts
     */
    @Select("SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    post\n" +
            "WHERE\n" +
            "    `status` = 0 AND title like #{string}\n" +
            "ORDER BY post_id DESC\n" +
            "LIMIT #{start} , #{number}")
    @Results(value = {
            @Result(property = "postId",column = "post_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "likeNum",column = "like_num"),
            @Result(property = "commentNum",column = "comment_num")
    })
    List<Post> selectPostOrderByTime(@Param("start") int start,@Param("number") int number,@Param("string") String string);

    /**
     * get list of posts order by like_num
     * @param start start pos
     * @param number the number of one page
     * @return list of posts
     */
    @Select("SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    mlab.post\n" +
            "WHERE\n" +
            "     status = 0 AND title like #{string}\n" +
            "ORDER BY like_num DESC\n"+
            "LIMIT #{start} , #{number};")
    @Results(value = {
            @Result(property = "postId",column = "post_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "likeNum",column = "like_num"),
            @Result(property = "commentNum",column = "comment_num")
    })
    List<Post> selectPostOrderByLikeNum(@Param("start") int start,@Param("number")int number,@Param("string") String string);
}
