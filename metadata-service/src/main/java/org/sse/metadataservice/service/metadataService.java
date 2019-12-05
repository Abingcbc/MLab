package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.DatasetMapper;
import org.sse.metadataservice.model.Dataset;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class metadataService {

    @Autowired
    DatasetMapper datasetMapper;

    public List<Dataset> getAllDatasetByUsername(String username) {
        return datasetMapper.getAllDatasetByUsername(username);
    }
}
