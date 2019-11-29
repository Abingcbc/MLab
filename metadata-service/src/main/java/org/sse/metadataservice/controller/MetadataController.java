package org.sse.metadataservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cbc
 */
@RestController
public class MetadataController {

    @GetMapping(value = "/test")
    public int getTest() {
        return 1;
    }
}
