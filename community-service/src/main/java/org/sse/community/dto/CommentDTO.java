package org.sse.community.dto;


import java.util.List;

/**
 * @author HPY
 */
public class CommentDTO {

  private long commentId;
  private long postId;
  private String username;
  private String content;
  private java.sql.Timestamp createTime;
  private long status;
  private long replyNum;
  private long likeNum;
  private String avatarUrl;

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }


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
