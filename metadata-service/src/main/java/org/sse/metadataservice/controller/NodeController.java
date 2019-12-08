package org.sse.metadataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.Node;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.NodeService;

import java.util.List;

/**
 * @author cbc
 */
@RestController
public class NodeController {

    @Autowired
    NodeService nodeService;

    @GetMapping(value = "/node/{username}")
    public List<Node> getAllNodeByUsername(@PathVariable String username) {
        return nodeService.getAllNodeByUsername(username);
    }

    @GetMapping(value = "/nodeId/{nodeId}")
    public Node getNodeById(@PathVariable Long nodeId) {
        return nodeService.getNodeById(nodeId);
    }

    @PostMapping(value = "/node")
    public ResponseEntity<Result> createNewNode(@RequestBody Node node) {
        Long nodeId = nodeService.createNewNode(node);
        if (nodeId > 0) {
            return new ResponseEntity<>(new Result(String.valueOf(nodeId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/node_delete")
    public void deleteNode(@RequestBody Long nodeId) {
        nodeService.deleteNode(nodeId);
    }

}
