package org.sse.metadataservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
