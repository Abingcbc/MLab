package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.*;
import org.sse.metadataservice.model.*;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class MetadataService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    PipelineMapper pipelineMapper;

    public List<Dataset> getAllDatasetByUsername(String username) {
        return datasetMapper.getAllDatasetByUsername(username);
    }

    public List<History> getAllTrainByUsername(String username) {
        return historyMapper.getAllTrainByUsername(username);
    }

    public List<History> getAllTestByUsername(String username) {
        return historyMapper.getAllTestByUsername(username);
    }

    public List<Model> getAllModelByUsername(String username) {
        return modelMapper.getAllModelByUsername(username);
    }

    public List<Node> getAllNodeByUsername(String username) {
        return nodeMapper.getAllNodeByUsername(username);
    }

    public List<Pipeline> getAllPipelineByUsername(String username) {
        return pipelineMapper.getAllPipelineByUsername(username);
    }

    public Long createNewDataset(Dataset dataset) {
        if (datasetMapper.createNewDataset(dataset) == 1) {
            return dataset.getDatasetId();
        } else {
            return (long) -1;
        }
    }

    public int getDatasetPermission(String username, Long datasetId) {
        Dataset dataset = datasetMapper.getDatasetById(datasetId);
        if (dataset.getIsPublic() == 1 || username.equals(dataset.getUsername())) {
            return 1;
        } else {
            return 0;
        }
    }

    public int checkDatasetOwner(String username, Long datasetId) {
        Dataset dataset = datasetMapper.getDatasetById(datasetId);
        if (username.equals(dataset.getUsername())) {
            return 1;
        } else {
            return 0;
        }
    }

}
