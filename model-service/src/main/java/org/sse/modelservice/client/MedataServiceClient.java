package org.sse.modelservice.client;

/**
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sse.modelservice.domain.model.PipelineInformation;
import org.sse.modelservice.domain.model.Response;

@FeignClient(name = "metadata-service")
public interface MedataServiceClient {
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/pipeline")
    ResponseEntity<Response> createNewPipeline(@RequestBody PipelineInformation pipelineInformation);
}
**/

