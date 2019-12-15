package org.sse.datasetservice.mapper;

import org.sse.datasetservice.model.DReply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZTL
 */
@Component
@Mapper
public interface DReplyMapper {
    /**
     * return the replies of a comment by inputting its comment id.
     * @param dCommentId identifier of comment
     * @return
     */
    @Select("select * " +
            "from d_reply " +
            "where d_comment_id = #{dCommentId}")
    List<DReply> getReplyByCommentId(@Param("dCommentId")Long dCommentId);

    /**
     * insert a reply of the comment identified by d_comment_id
     * @param username username
     * @param dCommentId identifier of comment
     * @param content the content of reply
     * @return insert successfully or not
     */
    @Insert("insert into d_reply(username,d_comment_id,content,create_time,status " +
            "values(#{myUsername},#{dCommentId},#{myContent},now(),0)")
    boolean insertReplyByCommentId(@Param("myUsername")String username,@Param("dCommentId")long dCommentId,
                                   @Param("myContent")String content);
}
