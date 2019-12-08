package org.sse.dataservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.sse.dataservice.client.MetadataServiceClient;

import java.security.Principal;

/**
 * @author cbc
 */
@Component
@Slf4j
public class UserSecurityService {

    @Autowired
    MetadataServiceClient metaDataServiceClient;

    public boolean checkSameUser(Authentication authentication, String fileId) {
        // Default getPrincipal() returns username which is only info we need here
        // so we choose not to customize the UserDetails.
        return metaDataServiceClient.checkDatasetOwner(
                (String)authentication.getPrincipal(), fileId) == 1;
    }

    public boolean checkDownloadPermission(Authentication authentication,
                                           String fileId) {
        return metaDataServiceClient.getDatasetPermission(
                (String)authentication.getPrincipal(), fileId) == 1;
    }
}
