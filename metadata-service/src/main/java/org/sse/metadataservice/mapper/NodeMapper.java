package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.sse.metadataservice.model.Node;

import java.util.List;

/**
 * @author cbc
 */
@Component
@Mapper
public interface NodeMapper {

    /**
     * get all nodes by username
     * @param username username
     * @return list of all nodes
     */
    @Select("select * from node where username = #{username};")
    List<Node> getAllNodeByUsername(@Param(value = "username") String username);

    /**
     * create new node
     * @param node object of node
     * @return num of affected row
     */
    @Insert("insert into node(username, name, create_time, status)\n" +
            "values (#{username}, #{name}, NOW(), 1);")
    @Options(useGeneratedKeys = true, keyProperty = "node_id")
    int createNewNode(Node node);

    /**
     * get node by node id
     * @param nodeId node id
     * @return object of node
     */
    @Select("select * from node where node_id = #{nodeId};")
    Node getNodeById(@Param("nodeId") Long nodeId);

    /**
     * delete node by node id
     * @param nodeId node id
     */
    @Update(value = "update node\n" +
            "set status = 0\n" +
            "where node_id = #{nodeId}\n")
    void deleteNodeById(@Param("nodeId") Long nodeId);
}
