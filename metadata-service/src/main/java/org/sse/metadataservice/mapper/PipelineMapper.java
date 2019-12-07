package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("select * from pipeline where username = #{username}")
    List<Pipeline> getAllPipelineByUsername(@Param(value = "username") String username);
}
