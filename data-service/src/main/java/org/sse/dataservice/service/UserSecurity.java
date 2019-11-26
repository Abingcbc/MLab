package org.sse.dataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.sse.dataservice.client.AccountServiceClient;

/**
 * @author cbc
 */
@Component
public class UserSecurity {

    @Autowired
    AccountServiceClient accountServiceClient;

    public boolean checkSameUser(Authentication authentication, String username) {
        return ((UserDetails)authentication.getPrincipal()).getUsername().equals(username);
    }

    public boolean checkDownloadPermission(Authentication authentication,
                                           String username, String filename) {
        if (checkSameUser(authentication, username)) {
            return true;
        } else {
            return accountServiceClient.getDatasetPermission(username, filename) == 1;
        }
    }
}
