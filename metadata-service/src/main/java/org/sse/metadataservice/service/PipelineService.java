package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.PipelineMapper;
import org.sse.metadataservice.model.Pipeline;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class PipelineService {


    @Autowired
    PipelineMapper pipelineMapper;


    public List<Pipeline> getAllPipelineByUsername(String username) {
        return pipelineMapper.getAllPipelineByUsername(username);
    }


    public Long createNewPipeline(Pipeline pipeline) {
        if (pipelineMapper.createNewPipeline(pipeline) == 1) {
            return pipeline.getPipelineId();
        } else {
            return (long) -1;
        }
    }


    public Pipeline getPipelineById(Long pipelineId) {
        return pipelineMapper.getPipelineById(pipelineId);
    }


    public void deletePipeline(Long pipelineId) {
        pipelineMapper.deletePipelineById(pipelineId);
    }


}
