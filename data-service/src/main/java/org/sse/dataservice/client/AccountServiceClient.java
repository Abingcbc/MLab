package org.sse.dataservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cbc
 */
@FeignClient(name = "account-service")
public interface AccountServiceClient {

    /**
     * request account service to get the permission of dataset
     * @param username username of dataset's owner
     * @param filename requested dataset
     * @return 1 means public, 0 means private
     */
    @RequestMapping(value = "/datasetPermission/{username}/{filename}", method = RequestMethod.GET)
    int getDatasetPermission(@PathVariable String username,
                             @PathVariable String filename);
}
