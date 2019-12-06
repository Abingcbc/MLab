package org.sse.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.community.model.Reply;

/**
 * @author HPY
 */
@Component
@Mapper
public interface ReplyMapper {

    /**
     * insert reply into Reply
     * @param username username
     * @param commentId commentId
     * @param content content
     * @return is inserted
     */
    @Insert("INSERT INTO reply (username,comment_id,content,create_time,status)\n" +
            "VALUES (#{username},#{commentId},#{content},now(),0)")
    boolean insertIntoReply(@Param("username") String username,
                            @Param("commentId") long commentId,
                            @Param("content") String content);

    /**
     *set Status =1
     * @param id
     * @return is set
     */
    @Update("UPDATE reply \n" +
            "SET \n" +
            "    status = 1\n" +
            "WHERE\n" +
            "    reply_id = #{replyId}")
    boolean setStatus(@Param("replyId") long id);

    /**
     * get reply by id
     * @param id reply id
     * @return reply
     */
    @Select("select * from reply where reply_id=#{replyId};")
    @Results(value = {
            @Result(property = "replyId",column = "reply_id"),
            @Result(property = "commentId",column = "comment_id"),
            @Result(property = "createTime",column = "create_time")
    })
    Reply getReplyByReplyId(@Param("replyId") long id);

    /**
     * set status = 1 by comment id
     * @param commentId comment id
     * @return is set
     */
    @Update("UPDATE reply \n" +
            "SET \n" +
            "    reply.status = 1\n" +
            "WHERE\n" +
            "    reply.comment_id = #{commentId}")
    boolean setStatusByCommentId(@Param("commentId") long commentId);
}
