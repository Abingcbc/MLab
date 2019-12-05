package org.sse.dataservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


}
