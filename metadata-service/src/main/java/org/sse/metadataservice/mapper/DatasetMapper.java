package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.Dataset;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface DatasetMapper {

    @Select(value = "select * from dataset where dataset_id = #{dataset_id}")
    Dataset getDatasetById(@Param(value = "dataset_id") Long dataset_id);

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
    @Update(value = "insert into dataset(username, dataset_name, description, format, size, create_time, is_public)\n" +
            "values (#{ds.username}, #{ds.dataset_name}, #{ds.description}, " +
            "#{ds.format}, #{ds.size}, #{ds.create_time}, #{ds.is_public})")
    int createNewDataset(@Param(value = "ds") Dataset dataset);
}
