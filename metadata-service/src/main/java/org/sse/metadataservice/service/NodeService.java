package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.NodeMapper;
import org.sse.metadataservice.model.Node;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    public List<Node> getAllNodeByUsername(String username) {
        return nodeMapper.getAllNodeByUsername(username);
    }

    public Long createNewNode(Node node) {
        if (nodeMapper.createNewNode(node) == 1) {
            return node.getNodeId();
        } else {
            return (long) -1;
        }
    }

    public Node getNodeById(Long nodeId) {
        return nodeMapper.getNodeById(nodeId);
    }

    public void deleteNode(Long nodeId) {
        nodeMapper.deleteNodeById(nodeId);
    }

}
