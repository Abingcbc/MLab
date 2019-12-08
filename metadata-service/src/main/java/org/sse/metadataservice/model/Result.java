package org.sse.metadataservice.model;

import lombok.Data;

/**
 * @author cbc
 */
@Data
public class Result {
    private String msg;

    public Result(String msg) {
        this.msg = msg;
    }
}
