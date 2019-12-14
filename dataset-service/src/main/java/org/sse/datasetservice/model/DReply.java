package org.sse.datasetservice.model;


import lombok.Data;

/**
 * @author ZTL
 */
@Data
public class DReply {

  private long dReplyId;
  private String username;
  private long dCommentId;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;

}
