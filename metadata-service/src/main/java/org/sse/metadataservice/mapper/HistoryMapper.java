package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.History;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface HistoryMapper {

    /**
     * get a list of user's train task
     * @param username username
     * @return a list of train task
     */
    @Select(value = "select * from history where username = #{username} and run_type = 1")
    List<History> getAllTrainByUsername(@Param(value = "username") String username);

    /**
     * get a list of user's test task
     * @param username username
     * @return a list of test task
     */
    @Select(value = "select * from history where username = #{username} and run_type = 0")
    List<History> getAllTestByUsername(@Param(value = "username") String username);
}
