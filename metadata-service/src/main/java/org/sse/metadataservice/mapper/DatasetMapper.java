package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.Dataset;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface DatasetMapper {

    /**
     * get a list of all user's dataset
     * @param username username
     * @return a list of all user's dataset
     */
    @Select(value = "select * from dataset where username = #{username}")
    List<Dataset> getAllDatasetByUsername(@Param(value = "username") String username);

    /**
     * create a new dataset
     * @param dataset dataset object
     * @return number of added row
     */
    @Insert(value = "insert into dataset(username, dataset_name, description, format, size, create_time, is_public, status)\n" +
            "values (#{username}, #{datasetName}, #{description}, " +
            "#{format}, #{size}, NOW(), #{isPublic}, 0);")
    @Options(useGeneratedKeys = true, keyProperty = "datasetId")
    int createNewDataset(Dataset dataset);

    /**
     * get dataset by dataset id
     * @param datasetId dataset id
     * @return metadata of dataset
     */
    @Select(value = "select * from dataset where dataset_id = #{datasetId}")
    Dataset getDatasetById(@Param(value = "datasetId") Long datasetId);

    /**
     * delete dataset by dataset id, only set status to 0
     * @param datasetId dataset id
     * @param status status
     */
    @Update(value = "update dataset\n" +
            "set status = #{status}\n" +
            "where dataset_id = #{datasetId}\n")
    void updateDatasetStatusById(@Param(value = "datasetId") Long datasetId,
                                 @Param(value = "status") Integer status);
}
