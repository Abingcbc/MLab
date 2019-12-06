package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.PipelineMapper;

@Service
public class PipelineService {
    @Autowired
    PipelineMapper pipelineMapper;
}
