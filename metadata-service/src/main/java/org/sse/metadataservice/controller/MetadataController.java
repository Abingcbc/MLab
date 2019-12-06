package org.sse.metadataservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.Dataset;

/**
 * @author cbc
 */
@RestController
public class MetadataController {

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetPermission/{username}/{fileId}")
    public int getDatasetPermission(@PathVariable String username,
                             @PathVariable String fileId) {
        return 1;
    }

    @PreAuthorize(value = "#oauth2.hasScope('server')")
    @GetMapping(value = "/datasetOwner/{username}/{fileId}")
    public int checkDatasetOwner(@PathVariable String username,
                                  @PathVariable String fileId) {
        return 1;
    }

//    @PostMapping(value = "/dataset")
//    public int createNewDataset(@RequestBody Dataset dataset) {
//
//    }
}
