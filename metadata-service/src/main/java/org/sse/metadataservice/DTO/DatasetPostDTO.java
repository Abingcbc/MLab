package org.sse.metadataservice.DTO;

import lombok.Data;

/**
 * @author ZTL
 */
@Data
public class DatasetPostDTO {
    private Long datasetId;
    private String username;
    private String datasetName;
    private String description;
    private String format;
    private Integer commentNum;
    private Double size;
    private java.sql.Timestamp createTime;
    private Long isPublic;
    private Long status;
    private String avatarUrl;
}
