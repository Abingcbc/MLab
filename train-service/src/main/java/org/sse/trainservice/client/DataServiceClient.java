package org.sse.trainservice.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@FeignClient(name = "data-service")
public interface DataServiceClient {

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/download/{fileId}/{format}")
    Response downloadFile(@PathVariable String fileId, @PathVariable String format);
}
