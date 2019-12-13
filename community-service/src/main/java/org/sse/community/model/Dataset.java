package org.sse.community.model;


/**
 * @author HPY
 */
public class Dataset {

  private long datasetId;
  private String username;
  private String datasetName;
  private String description;
  private String format;
  private double size;
  private java.sql.Timestamp createTime;
  private long isPublic;


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


  public String getDatasetName() {
    return datasetName;
  }

  public void setDatasetName(String datasetName) {
    this.datasetName = datasetName;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }


  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public long getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(long isPublic) {
    this.isPublic = isPublic;
  }

}
