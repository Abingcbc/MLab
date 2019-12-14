package org.sse.datasetservice.dto;

import lombok.Data;
import org.sse.datasetservice.model.DReply;

/**
 * @author cbc
 */
@Data
public class DReplyDto {
    private long dReplyId;
    private String username;
    private long dCommentId;
    private String content;
    private java.sql.Timestamp createTime;
    private long status;
    private String avatarUrl;

    public DReplyDto(DReply dReply, String avatarUrl) {
        this.dReplyId = dReply.getDReplyId();
        this.username = dReply.getUsername();
        this.dCommentId = dReply.getDCommentId();
        this.content = dReply.getContent();
        this.createTime = dReply.getCreateTime();
        this.status = dReply.getStatus();
        this.avatarUrl = avatarUrl;
    }
}
