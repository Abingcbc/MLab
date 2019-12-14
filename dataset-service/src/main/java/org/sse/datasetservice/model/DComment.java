package org.sse.datasetservice.model;


import lombok.Data;

/**
 * @author ZTL
 */
@Data
public class DComment {

  private long dCommentId;
  private long datasetId;
  private String username;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;
  private long replyNum;
  private long likeNum;

}
