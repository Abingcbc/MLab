package org.sse.metadataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.*;
import org.sse.metadataservice.service.MetadataService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cbc
 */
@RestController
public class MetadataController {

    @Autowired
    MetadataService metadataService;

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetPermission/{username}/{datasetId}")
    public int getDatasetPermission(@PathVariable String username,
                             @PathVariable Long datasetId) {
        return metadataService.getDatasetPermission(username, datasetId);
    }

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetOwner/{username}/{datasetId}")
    public int checkDatasetOwner(@PathVariable String username,
                                  @PathVariable Long datasetId) {
        return metadataService.checkDatasetOwner(username, datasetId);
    }

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @PostMapping(value = "/dataset")
    public Result createNewDataset(@RequestBody Dataset dataset,
                                   HttpServletResponse response) {
        Long datasetId = metadataService.createNewDataset(dataset);
        if (datasetId > 0) {
            response.setStatus(HttpServletResponse.SC_OK);
            return new Result(String.valueOf(datasetId));
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @GetMapping(value = "/dataset/{username}")
    public List<Dataset> getAllDatasetByUsername(@PathVariable String username) {
        return metadataService.getAllDatasetByUsername(username);
    }

    @GetMapping(value = "/train/{username}")
    public List<History> getAllTrainByUsername(@PathVariable String username) {
        return metadataService.getAllTrainByUsername(username);
    }

    @GetMapping(value = "/test/{username}")
    public List<History> getAllTestByUsername(@PathVariable String username) {
        return metadataService.getAllTestByUsername(username);
    }

    @GetMapping(value = "/model/{username}")
    public List<Model> getAllModelByUsername(@PathVariable String username) {
        return metadataService.getAllModelByUsername(username);
    }

    @GetMapping(value = "/node/{username}")
    public List<Node> getAllNodeByUsername(@PathVariable String username) {
        return metadataService.getAllNodeByUsername(username);
    }

    @GetMapping(value = "/pipeline/{username}")
    public List<Pipeline> getAllPipelineByUsername(@PathVariable String username) {
        return metadataService.getAllPipelineByUsername(username);
    }

}
