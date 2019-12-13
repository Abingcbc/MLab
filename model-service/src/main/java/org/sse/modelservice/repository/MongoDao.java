package org.sse.modelservice.repository;

import org.springframework.stereotype.Repository;
import org.sse.modelservice.domain.model.Graph;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @version: V1.0
 * @author: cyy
 * @className: MongoDao
 * @packageName: com.mlab.Repository
 * @description: Dao for graph structure
 * @data: 2019/12/2 下午8:42
 **/
@Repository
public interface MongoDao extends MongoRepository<Graph, String> {
    Graph findByGraphId(String graphId);
}
