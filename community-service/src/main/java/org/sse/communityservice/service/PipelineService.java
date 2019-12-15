package org.sse.communityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.communityservice.mapper.PipelineMapper;

@Service
public class PipelineService {
    @Autowired
    PipelineMapper pipelineMapper;
}
