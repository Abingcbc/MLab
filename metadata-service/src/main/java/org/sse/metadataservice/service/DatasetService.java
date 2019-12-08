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
public class DatasetService {

    @Autowired
    DatasetMapper datasetMapper;

    public List<Dataset> getAllDatasetByUsername(String username) {
        return datasetMapper.getAllDatasetByUsername(username);
    }

    public Long createNewDataset(Dataset dataset) {
        if (datasetMapper.createNewDataset(dataset) == 1) {
            return dataset.getDatasetId();
        } else {
            return (long) -1;
        }
    }

    public Dataset getDatasetById(Long datasetId) {
        return datasetMapper.getDatasetById(datasetId);
    }

    public void deleteDataset(Long datasetId) {
        datasetMapper.deleteDatasetById(datasetId);
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
