package org.sse.metadataservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.DTO.DatasetPostDTO;
import org.sse.metadataservice.model.Dataset;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.DatasetService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cbc
 */
@RestController
@Slf4j
public class DatasetController {

    @Autowired
    DatasetService datasetService;


    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetPermission/{username}/{datasetId}")
    public int getDatasetPermission(@PathVariable String username,
                                    @PathVariable Long datasetId) {
        return datasetService.getDatasetPermission(username, datasetId);
    }

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetOwner/{username}/{datasetId}")
    public int checkDatasetOwner(@PathVariable String username,
                                 @PathVariable Long datasetId) {
        return datasetService.checkDatasetOwner(username, datasetId);
    }

    @GetMapping(value = "/dataset/{username}/{pageNum}/{pageSize}")
    public PageInfo<Dataset> getDatasetPageByUsername(@PathVariable String username,
                                                  @PathVariable int pageNum,
                                                  @PathVariable int pageSize) {
        return datasetService.getPageDatasetByUsername(username, pageNum, pageSize);
    }

    @GetMapping(value = "/datasetId/{datasetId}")
    public Dataset getDatasetById(@PathVariable Long datasetId) {
        return datasetService.getDatasetById(datasetId);
    }

    @GetMapping(value = "/datasetPostId/{datasetId}")
    public DatasetPostDTO getDatasetPostById(@PathVariable Long datasetId) {
        return datasetService.getDatasetPostById(datasetId);
    }

    @PostMapping(value = "/dataset")
    public ResponseEntity<Result> createNewDataset(@RequestBody Dataset dataset) {
        Long datasetId = datasetService.createNewDataset(dataset);
        if (datasetId > 0) {
            return new ResponseEntity<>(new Result(String.valueOf(datasetId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/dataset_delete")
    public void deleteDataset(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        if (datasetService.updateDatasetStatus(jsonObject.getLong("deleteId"), 1) == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    }

    @PostMapping(value = "/dataset_enable")
    public void enableDataset(@RequestBody Long datasetId) {
        datasetService.updateDatasetStatus(datasetId, 0);
    }

    @GetMapping(value = "/dataset_search/{keyWord}/{pageNum}/{pageSize}")
    public PageInfo<DatasetPostDTO> getAllDatasetByKeyword(@PathVariable String keyWord,
                                                  @PathVariable int pageNum,
                                                  @PathVariable int pageSize) {
        return datasetService.getDatasetPostByString(keyWord, pageNum, pageSize);
    }

    @GetMapping(value = "/dataset_search/{pageNum}/{pageSize}")
    public PageInfo<DatasetPostDTO> getAllDataset(@PathVariable int pageNum,
                                                  @PathVariable int pageSize) {
        return datasetService.getAllDatasetPost(pageNum, pageSize);
    }

    @PostMapping(value = "/edit_dataset")
    public  boolean updateDatasetDesAndPul(@RequestBody Dataset dataset){
        log.warn(dataset.toString());
        return  datasetService.updateDatasetDesAndPul(dataset.getDatasetId(),dataset.getIsPublic(), dataset.getDescription());
    }
}
