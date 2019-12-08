package org.sse.metadataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.Pipeline;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.PipelineService;

import java.util.List;

/**
 * @author cbc
 */
@RestController
public class PipelineController {

    @Autowired
    PipelineService pipelineService;

    @GetMapping(value = "/pipeline/{username}")
    public List<Pipeline> getAllPipelineByUsername(@PathVariable String username) {
        return pipelineService.getAllPipelineByUsername(username);
    }

    @GetMapping(value = "/pipelineId/{pipelineId}")
    public Pipeline getPipelineById(@PathVariable Long pipelineId) {
        return pipelineService.getPipelineById(pipelineId);
    }

    @PostMapping(value = "/pipeline")
    public ResponseEntity<Result> createNewPipeline(@RequestBody Pipeline pipeline) {
        Long pipelineId = pipelineService.createNewPipeline(pipeline);
        if (pipelineId > 0) {
            return new ResponseEntity<>(new Result(String.valueOf(pipelineId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/pipeline_delete")
    public void deletePipeline(@RequestBody Long pipelineId) {
        pipelineService.deletePipeline(pipelineId);
    }

}
