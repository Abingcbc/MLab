package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.*;
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

    /**
     * create new model
     * @param model object of model
     * @return num of affected row
     */
    @Insert("insert into model(username, model_name, description, " +
            "create_time, status)\n" +
            "values (#{username}, #{modelName}, #{description}," +
            "NOW(), 1)")
    int createNewModel(Model model);

    /**
     * get model by model id
     * @param modelId model id
     * @return object of model
     */
    @Select("select * from model where model_id = #{modelId}")
    Model getModelById(@Param("modelId") Long modelId);

    /**
     * delete model by model id
     * @param modelId model id
     */
    @Update(value = "update model\n" +
            "set status = 0\n" +
            "where model_id = #{modelId}\n")
    void deleteModelById(@Param("modelId") Long modelId);
}
