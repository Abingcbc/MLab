package org.sse.metadataservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cbc
 */
@FeignClient
public interface DatasetServiceClient {

    /**
     * get comment num by dataset id
     * @param datasetId dataset id
     * @return comment num
     */
    @GetMapping("/commentNum/{datasetId}")
    int getCommentNumByDatasetId(@PathVariable Long datasetId);
}
