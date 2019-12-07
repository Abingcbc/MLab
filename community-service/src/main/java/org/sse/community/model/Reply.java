package org.sse.community.model;


public class Reply {

  private long replyId;
  private String username;
  private long commentId;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;


  public long getReplyId() {
    return replyId;
  }

  public void setReplyId(long replyId) {
    this.replyId = replyId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
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
