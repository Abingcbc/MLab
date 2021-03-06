package org.sse.communityservice.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.communityservice.dto.CommentDTO;
import org.sse.communityservice.model.Comment;

import java.util.List;

/**
 * @author HPY
 */
@Component
@Mapper
public interface CommentMapper {
    /**
     * insert comment into Comment
     * @return is inserted
     */
    @Insert("insert into comment (post_id,username,content,create_time,`status`,reply_num,like_num)\n" +
            "values(#{postId},#{username},#{content},now(),0,0,0)")
    @Options(useGeneratedKeys = true, keyProperty = "commentId",keyColumn = "comment_id")
    long insertIntoComment(Comment comment);

    /**
     * get comment by id
     * @param Id comment-id
     * @return comment
     */
    @Select("SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    comment\n" +
            "WHERE\n" +
            "    comment_id = #{id}")
    @Results(value ={
            @Result(property = "commentId",column = "comment_id"),
            @Result(property = "postId",column = "post_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "replyNum",column = "reply_num"),
            @Result(property = "likeNum",column = "like_num")
        }
    )
    Comment getCommentById(@Param("id") long Id);

    /**
     * get comments by post id
     * @param postId post id
     * @param start start
     * @param commentNum comment num
     * @return list of comments
     */
    @Select("SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    comment\n" +
            "WHERE\n" +
            "    post_id = #{postId} AND `status` = 0\n" +
            "ORDER BY comment_id DESC\n" )
    @Results(value ={
            @Result(property = "commentId",column = "comment_id"),
            @Result(property = "postId",column = "post_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "replyNum",column = "reply_num"),
            @Result(property = "likeNum",column = "like_num")
        }
    )
    List<CommentDTO> getCommentsByPostId(@Param("postId") long postId);


    /**
     * set comment status = 1
     * @param commentId commentId
     * @return is set
     */
    @Update("UPDATE comment \n" +
            "SET \n" +
            "    status = 1\n" +
            "WHERE\n" +
            "    comment_id = #{commentId}")
    boolean setStatus(@Param("commentId") long commentId);



    /**
     * add like num
     * @param commentId comment id
     * @return is added
     */
    @Update("UPDATE comment \n" +
            "SET \n" +
            "    like_num = like_num + 1\n" +
            "WHERE\n" +
            "    comment_id = #{commentId}")
    boolean addLikeNum(@Param("commentId") long commentId);

    /**
     * reduce like num
     * @param commentId comment id
     * @return is reduced
     */
    @Update("UPDATE comment \n" +
            "SET \n" +
            "    like_num = like_num - 1\n" +
            "WHERE\n" +
            "    comment_id = #{commentId}")
    boolean reduceLikeNum(@Param("commentId") long commentId);

    /**
     * add reply num
     * @param commentId comment id
     * @return is added
     */
    @Update("UPDATE comment \n" +
            "SET \n" +
            "    reply_num = reply_num + 1\n" +
            "WHERE\n" +
            "    comment_id = #{commentId}")
    boolean addReplyNum(@Param("commentId") long commentId);

    /**
     * reduce reply num
     * @param commentId comment id
     * @return is reduced
     */
    @Update("UPDATE comment \n" +
            "SET \n" +
            "    reply_num = reply_num - 1\n" +
            "WHERE\n" +
            "    comment_id = #{commentId}")
    boolean reduceReplyNum(@Param("commentId") long commentId);

    /**
     * set status = 1 by post id
     * @param postId post id
     * @return is set
     */
    @Update("UPDATE `comment` \n" +
            "SET \n" +
            "    `comment`.status = 1\n" +
            "WHERE\n" +
            "    `comment`.post_id = #{post_id}")
    boolean setStatusByPostId(@Param("post_id") long postId);
}
