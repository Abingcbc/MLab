package org.sse.dataservice.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author cbc
 */
@Data
public class Chunk implements Serializable {

    private Long id;
    private Integer chunkNumber;
    private Long chunkSize;
    private Long currentChunkSize;
    private Long totalSize;
    private Long identifier;
    private String filename;
    private String relativePath;
    private Long totalChunks;
    private String type;
    private MultipartFile file;

}

