package org.sse.metadataservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
