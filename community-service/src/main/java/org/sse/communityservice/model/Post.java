package org.sse.communityservice.model;


public class Post {

  private long postId;
  private String username;
  private String title;
  private String content;
  private java.sql.Timestamp createTime;
  private long likeNum;
  private long commentNum;
  private long status;


  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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


  public long getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(long likeNum) {
    this.likeNum = likeNum;
  }


  public long getCommentNum() {
    return commentNum;
  }

  public void setCommentNum(long commentNum) {
    this.commentNum = commentNum;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
