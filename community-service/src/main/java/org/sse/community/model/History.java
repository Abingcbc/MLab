package org.sse.community.model;


/**
 * @author HPY
 */
public class History {

  private long trainId;
  private long runType;
  private String username;
  private long pipelineId;
  private long modelId;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;


  public long getTrainId() {
    return trainId;
  }

  public void setTrainId(long trainId) {
    this.trainId = trainId;
  }


  public long getRunType() {
    return runType;
  }

  public void setRunType(long runType) {
    this.runType = runType;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public long getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(long pipelineId) {
    this.pipelineId = pipelineId;
  }


  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }


  public java.sql.Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Timestamp startTime) {
    this.startTime = startTime;
  }


  public java.sql.Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(java.sql.Timestamp endTime) {
    this.endTime = endTime;
  }

}
