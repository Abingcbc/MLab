package org.sse.metadataservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.DTO.DatasetPostDTO;
import org.sse.metadataservice.client.DatasetServiceClient;
import org.sse.metadataservice.client.UserServiceClient;
import org.sse.metadataservice.mapper.DatasetMapper;
import org.sse.metadataservice.model.Dataset;

import java.util.List;

/**
 * @author cbc
 */
@Service
@Slf4j
public class DatasetService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    DatasetServiceClient datasetServiceClient;

    public PageInfo<Dataset> getPageDatasetByUsername(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Dataset> datasetList = datasetMapper.getAllDatasetByUsername(username);
        return new PageInfo<>(datasetList);
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

    public DatasetPostDTO getDatasetPostById(Long datasetId) {
        DatasetPostDTO datasetPostDTO = datasetMapper.getDatasetPostById(datasetId);
        datasetPostDTO.setAvatarUrl(userServiceClient.getUserAvatarUrlByUsername(datasetPostDTO.getUsername()));
        datasetPostDTO.setCommentNum(datasetServiceClient.getCommentNumByDatasetId(datasetPostDTO.getDatasetId()));
        return datasetPostDTO;
    }

    public int updateDatasetStatus(Long datasetId, Integer status) {
        int s = datasetMapper.updateDatasetStatusById(datasetId, status);
        log.warn(String.valueOf(s));
        return s;
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

    public PageInfo<DatasetPostDTO> getDatasetPostByString(String keyword, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        keyword = "%" + keyword + "%";
        List<DatasetPostDTO> datasetPostDTOList = datasetMapper.selectDatasetByKeyword(keyword);
        for (DatasetPostDTO dto: datasetPostDTOList) {
            dto.setAvatarUrl(userServiceClient.getUserAvatarUrlByUsername(dto.getUsername()));
            dto.setCommentNum(datasetServiceClient.getCommentNumByDatasetId(dto.getDatasetId()));
        }
        return new PageInfo<>(datasetPostDTOList);
    }

    public PageInfo<DatasetPostDTO> getAllDatasetPost(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<DatasetPostDTO> datasetPostDTOList = datasetMapper.selectAllDataset();
        for (DatasetPostDTO dto: datasetPostDTOList) {
            dto.setAvatarUrl(userServiceClient.getUserAvatarUrlByUsername(dto.getUsername()));
            dto.setCommentNum(datasetServiceClient.getCommentNumByDatasetId(dto.getDatasetId()));
        }
        return new PageInfo<>(datasetPostDTOList);
    }

    public boolean updateDatasetDesAndPul(Long datasetId, Long isPublic, String description){
        return  datasetMapper.updateDatasetDesAndPul(datasetId,isPublic,description);
    }
}
