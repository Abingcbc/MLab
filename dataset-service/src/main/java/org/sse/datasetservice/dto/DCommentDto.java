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

    public DCommentDto(DComment dComment, String avatarUrl) {
        this.dCommentId = dComment.getDCommentId();
        this.datasetId = dComment.getDatasetId();
        this.username = dComment.getUsername();
        this.content = dComment.getContent();
        this.createTime = dComment.getCreateTime();
        this.status = dComment.getStatus();
        this.replyNum = dComment.getReplyNum();
        this.likeNum = dComment.getLikeNum();
        this.avatarUrl = avatarUrl;
    }
}
