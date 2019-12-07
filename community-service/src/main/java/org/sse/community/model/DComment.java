package org.sse.community.model;


/**
 * @author HPY
 */
public class DComment {

  private long dCommentId;
  private long datasetId;
  private String username;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;
  private long replyNum;
  private long likeNum;


  public long getDCommentId() {
    return dCommentId;
  }

  public void setDCommentId(long dCommentId) {
    this.dCommentId = dCommentId;
  }


  public long getDatasetId() {
    return datasetId;
  }

  public void setDatasetId(long datasetId) {
    this.datasetId = datasetId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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


  public long getReplyNum() {
    return replyNum;
  }

  public void setReplyNum(long replyNum) {
    this.replyNum = replyNum;
  }


  public long getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(long likeNum) {
    this.likeNum = likeNum;
  }

}
