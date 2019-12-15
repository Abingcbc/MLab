package org.sse.trainservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "data-service")
public interface DataServiceClient {
}
