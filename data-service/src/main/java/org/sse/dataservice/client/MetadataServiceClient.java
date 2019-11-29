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
     * @param filename requested dataset
     * @return 1 means public, 0 means private
     */
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetPermission/{username}/{filename}")
    int getDatasetPermission(@PathVariable String username,
                             @PathVariable String filename);

}
