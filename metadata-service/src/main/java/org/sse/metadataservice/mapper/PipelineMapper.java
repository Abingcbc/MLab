package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.Pipeline;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface PipelineMapper {

    /**
     * get all pipelines by username
     * @param username username
     * @return list of all pipelines
     */
    @Select("select * from pipeline where username = #{username} and status = 0;")
    List<Pipeline> getAllPipelineByUsername(@Param(value = "username") String username);

    /**
     * create new pipeline
     * @param pipeline object of pipeline
     * @return num of affected row
     */
    @Insert("insert into pipeline(username, pipeline_name, " +
            "description, create_time,input_file, status) \n" +
            "values (#{username}, #{pipelineName}, #{description}," +
            "NOW(),#{inputFile} ,0);")
    @Options(useGeneratedKeys = true, keyProperty = "pipelineId")
    int createNewPipeline(Pipeline pipeline);

    /**
     * get pipeline by pipeline id
     * @param pipelineId pipeline id
     * @return object of pipeline
     */
    @Select("select * from pipeline where pipeline_id = #{pipelineId} and status = 0;")
    Pipeline getPipelineById(@Param("pipelineId") Long pipelineId);

    /**
     * get pipeline by pipeline id
     * @param pipelineId pipeline id
     */
    @Update(value = "update pipeline\n" +
            "set status = 1\n" +
            "where pipeline_id = #{pipelineId}\n")
    void deletePipelineById(@Param("pipelineId") Long pipelineId);
}
