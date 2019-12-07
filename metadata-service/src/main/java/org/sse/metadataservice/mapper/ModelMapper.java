package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.Model;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface ModelMapper {

    /**
     * get all models by username
     * @param username username
     * @return list of all models
     */
    @Select("select * from model where username = #{username};")
    List<Model> getAllModelByUsername(@Param(value = "username") String username);
}
