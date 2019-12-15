package org.sse.metadataservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cbc
 */
@FeignClient(name = "user-service")
public interface UserServiceClient {

    /**
     * get user's avatar url
     * @param username username
     * @return avatar url
     */
    @GetMapping(value = "/avatar/{username}")
    String getUserAvatarUrlByUsername(@PathVariable("username") String username);
}
