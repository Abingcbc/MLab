package org.sse.community.model;


public class Like {

  private long likeId;
  private long typeId;
  private long type;
  private String username;
  private long status;
  private java.sql.Timestamp createTime;


  public long getLikeId() {
    return likeId;
  }

  public void setLikeId(long likeId) {
    this.likeId = likeId;
  }


  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long typeId) {
    this.typeId = typeId;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
