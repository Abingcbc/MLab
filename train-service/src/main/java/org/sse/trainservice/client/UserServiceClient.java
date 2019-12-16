package org.sse.trainservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.sse.trainservice.domain.User;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/user/info/{username}")
    User getUserInfoWithoutPassword(@PathVariable String username);
}
