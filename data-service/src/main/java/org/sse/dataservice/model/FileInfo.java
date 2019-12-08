package org.sse.dataservice.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cbc
 */
@Data
public class FileInfo implements Serializable {

    private String username;
    private String datasetName;
    private String description;
    private Long size;
    private String format;
    private Long isPublic;
    private String identifier;
    private Integer totalChunkNum;
}
