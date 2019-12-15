package org.sse.communityservice.model;


public class DReply {

  private long dReplyId;
  private String username;
  private long dCommentId;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;


  public long getDReplyId() {
    return dReplyId;
  }

  public void setDReplyId(long dReplyId) {
    this.dReplyId = dReplyId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getDCommentId() {
    return dCommentId;
  }

  public void setDCommentId(long dCommentId) {
    this.dCommentId = dCommentId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
