package org.sse.community.model;


/**
 * @author HPY
 */
public class Pipeline {

  private long pipelineId;
  private String username;
  private String pipelineName;
  private String description;
  private java.sql.Timestamp createTime;


  public long getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(long pipelineId) {
    this.pipelineId = pipelineId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPipelineName() {
    return pipelineName;
  }

  public void setPipelineName(String pipelineName) {
    this.pipelineName = pipelineName;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
