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
    public long createNewPipeline(@RequestBody Pipeline pipeline) {
        Long pipelineId = pipelineService.createNewPipeline(pipeline);
        return pipelineId;
    }

    @PostMapping(value = "/pipeline_delete")
    public void deletePipeline(@RequestBody Long pipelineId) {
        pipelineService.deletePipeline(pipelineId);
    }

}
