package org.sse.communityservice.model;


public class Node {

  private long nodeId;
  private String username;
  private String name;
  private java.sql.Timestamp createTime;


  public long getNodeId() {
    return nodeId;
  }

  public void setNodeId(long nodeId) {
    this.nodeId = nodeId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
