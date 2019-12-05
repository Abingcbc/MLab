package org.sse.dataservice.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cbc
 */
@Data
public class FileInfo implements Serializable {

    private Long id;
    private String filename;
    private String identifier;
    private Long totalSize;
    private String type;
    private Integer totalChunkNum;
}
