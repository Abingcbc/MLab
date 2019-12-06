package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.ModelMapper;


@Service
public class ModelService {
    @Autowired
    ModelMapper modelMapper;
}
