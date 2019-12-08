package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.*;
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
    @Select(value = "select * from history where username = #{username} and run_type = 1;")
    List<History> getAllTrainByUsername(@Param(value = "username") String username);

    /**
     * get a list of user's test task
     * @param username username
     * @return a list of test task
     */
    @Select(value = "select * from history where username = #{username} and run_type = 0;")
    List<History> getAllTestByUsername(@Param("username") String username);

    /**
     * create new history (train or test)
     * @param history train or test
     * @return num of affected row
     */
    @Insert(value = "insert into history (run_type, username, " +
            "pipeline_id, model_id, start_time, status)\n" +
            "values (#{runType}, #{username}, #{pipelineId}," +
            "#{modelId}, NOW(), 1);")
    @Options(useGeneratedKeys = true, keyProperty = "history_id")
    int createNewHistory(History history);

    /**
     * get history by history id
     * @param historyId history id
     * @return object of history
     */
    @Select(value = "select * from history where history_id = #{historyId};")
    History getHistoryById(@Param("historyId") Long historyId);

    /**
     * delete history by history id
     * @param historyId history id
     */
    @Update(value = "update history\n" +
            "set status = 0\n" +
            "where history_id = #{historyId};")
    void deleteHistoryById(@Param("historyId") Long historyId);
}
