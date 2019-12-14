package org.sse.metadataservice.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.DTO.DatasetPostDTO;
import org.sse.metadataservice.model.Dataset;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.DatasetService;

import java.util.List;

/**
 * @author cbc
 */
@RestController
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

    @GetMapping(value = "/dataset/{username}")
    public List<Dataset> getAllDatasetByUsername(@PathVariable String username) {
        return datasetService.getAllDatasetByUsername(username);
    }

    @GetMapping(value = "/datasetId/{datasetId}")
    public Dataset getDatasetById(@PathVariable Long datasetId) {
        return datasetService.getDatasetById(datasetId);
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
    public void deleteDataset(@RequestBody Long datasetId) {
        datasetService.updateDatasetStatus(datasetId, 0);
    }

    @PostMapping(value = "/dataset_enable")
    public void enableDataset(@RequestBody Long datasetId) {
        datasetService.updateDatasetStatus(datasetId, 1);
    }

    @GetMapping(value = "/dataset/{keyWord}/{pageNum}/{pageSize}")
    public PageInfo<DatasetPostDTO> getAllDataset(@PathVariable String keyWord,
                                                  @PathVariable int pageNum,
                                                  @PathVariable int pageSize) {
        return datasetService.getDatasetPostByString(keyWord, pageNum, pageSize);
    }

}
