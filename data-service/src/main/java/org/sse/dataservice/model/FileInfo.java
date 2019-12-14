package org.sse.dataservice.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cbc
 */
@Data
public class FileInfo implements Serializable {

    private String datasetName;
    private String description;
    private Long size;
    private String format;
    private Long isPublic;
    private Long identifier;
    private Long totalChunkNum;
}
