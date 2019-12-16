package org.sse.datasetservice.mapper;

import org.sse.datasetservice.dto.DCommentDto;
import org.sse.datasetservice.model.DComment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZTL
 */
@Component
@Mapper
public interface DCommentMapper {
    /**
     * get the comments of the dataset identified by the datasetId
     * @param datasetId identifier of dataset
     * @return a list of comment
     */
    @Select("select * " +
            "from d_comment " +
            "where dataset_id = #{datasetId} " +
            "order by d_comment_id desc ")
    List<DCommentDto> getCommentByDatasetId(@Param("datasetId")Long datasetId);

    @Update("update d_comment " +
            "set reply_num = reply_num + 1 " +
            "where d_comment_id = #{dCommentId}")
    boolean updateDCommentReplyNum(@Param("dCommentId")Long dCommentId);

    /**
     * insert a comment
     * @param comment the class of comment
     * @return insert successfully or not
     */
    @Insert("insert into d_comment(dataset_id,username,content,create_time,`status`,reply_num,like_num) " +
            "values(#{datasetId},#{username},#{content},now(),0,0,0)")
    @Options(useGeneratedKeys = true, keyProperty = "dCommentId",keyColumn = "d_comment_id")
    boolean insertCommentByDatasetId(DComment comment);
}
