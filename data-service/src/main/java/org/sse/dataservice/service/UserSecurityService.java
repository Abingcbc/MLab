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

    public boolean checkSameUser(Authentication authentication, String username) {
        // Default getPrincipal() returns username which is only info we need here
        // so we choose not to customize the UserDetails.
        return authentication.getPrincipal().equals(username);
    }

    public boolean checkDownloadPermission(Authentication authentication,
                                           String username, String filename) {
        if (checkSameUser(authentication, username)) {
            return true;
        } else {
            return metaDataServiceClient.getDatasetPermission(username, filename) == 1;
        }
    }
}
