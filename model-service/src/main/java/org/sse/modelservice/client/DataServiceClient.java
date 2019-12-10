package org.sse.modelservice.client;

/**
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@FeignClient(name = "data-service")
public interface DataServiceClient {
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/upload/{fileId}/{chunkId}")
    void uploadFile(@RequestParam("file") MultipartFile file,
                    HttpServletResponse response,
                    @PathVariable String fileId,
                    @PathVariable int chunkId);
}
**/
