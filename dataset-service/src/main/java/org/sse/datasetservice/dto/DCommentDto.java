package org.sse.datasetservice.dto;

import lombok.Data;
import org.sse.datasetservice.model.DComment;

/**
 * @author cbc
 */
@Data
public class DCommentDto {

    private long dCommentId;
    private long datasetId;
    private String username;
    private String content;
    private java.sql.Timestamp createTime;
    private long status;
    private long replyNum;
    private long likeNum;
    private String avatarUrl;
}
