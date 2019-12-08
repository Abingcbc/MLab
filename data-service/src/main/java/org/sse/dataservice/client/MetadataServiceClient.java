package org.sse.dataservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sse.dataservice.model.Dataset;
import org.sse.dataservice.model.Result;

/**
 * @author cbc
 */
@FeignClient(name = "metadata-service")
public interface MetadataServiceClient {

    /**
     * request metadata service to get the permission of dataset
     * @param username username of dataset's owner
     * @param fileId requested dataset
     * @return 1 means public, 0 means private
     */
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetPermission/{username}/{fileId}")
    int getDatasetPermission(@PathVariable String username,
                             @PathVariable String fileId);

    /**
     * check the dataset's owner's username
     * @param username dataset id
     * @param fileId dataset i
     * @return 1 means OK, 0 means unauthenticated
     */
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetOwner/{username}/{fileId}")
    int checkDatasetOwner(@PathVariable String username,
                          @PathVariable String fileId);

    /**
     * create new dataset
     * @param dataset object of dataset
     * @return 200 or 500
     */
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @PostMapping(value = "/dataset")
    ResponseEntity<Result> createNewDataset(@RequestBody Dataset dataset);

    /**
     * delete dataset
     * @param datasetId
     */
    @PostMapping(value = "/dataset_delete")
    void deleteDataset(@RequestBody Long datasetId);
}
