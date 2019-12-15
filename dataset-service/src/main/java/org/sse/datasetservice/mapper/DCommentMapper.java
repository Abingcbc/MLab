package org.sse.datasetservice.mapper;

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
            "where dataset_id = #{datasetId}")
    List<DComment> getCommentByDatasetId(@Param("datasetId")long datasetId);

    /**
     * insert a comment
     * @param datasetId identifier of dataset
     * @param username user name
     * @param content the content of comment
     * @return insert successfully or not
     */
    @Insert("insert into d_comment(dataset_id,username,content,create_time,`status`,reply_num,like_num) " +
            "values(#{datasetId},#{myUsername},#{myContent},now(),0,0,0)")
    boolean insertCommentByDatasetId(@Param("datasetId")long datasetId,@Param("myUsername")String username,@Param("myContent")String content);
}
