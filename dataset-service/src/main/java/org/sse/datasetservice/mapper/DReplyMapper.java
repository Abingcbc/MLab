package org.sse.datasetservice.mapper;

import org.sse.datasetservice.dto.DReplyDto;
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
    List<DReplyDto> getReplyByCommentId(@Param("dCommentId")Long dCommentId);

    /**
     * insert a reply of the comment identified by d_comment_id
     * @param dReply class of reply
     * @return
     */
    @Insert("insert into d_reply(username,d_comment_id,content,create_time,status) " +
            "values(#{username},#{dCommentId},#{content},now(),0)")
    @Options(useGeneratedKeys = true, keyProperty = "dReplyId",keyColumn = "d_reply_id")
    boolean insertReplyByCommentId(DReply dReply);
}
