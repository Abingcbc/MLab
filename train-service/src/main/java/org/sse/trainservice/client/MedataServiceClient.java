package org.sse.trainservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.sse.trainservice.domain.Model;
import org.sse.trainservice.domain.History;

@FeignClient(name = "metadata-service")
public interface MedataServiceClient {
    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/model")
    long createNewModel(@RequestBody Model Model);

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/history")
    long createNewHistory(@RequestBody History history);

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/history_status/{historyId}/{status}")
    void setHistory(@PathVariable Long historyId, @PathVariable Integer status);

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/history/{historyId}")
    void setEndTime(@PathVariable Long historyId);
}
