package org.sse.dataservice.model;


import lombok.Data;

/**
 * @author cbc
 */
@Data
public class Dataset {

  private long datasetId;
  private String username;
  private String datasetName;
  private String description;
  private String format;
  private double size;
  private java.sql.Timestamp createTime;
  private long isPublic;
  private long status;

  public Dataset(String username, String datasetName,
                 String description, String format,
                 double size, long isPublic) {
    this.username = username;
    this.datasetName = datasetName;
    this.description = description;
    this.format = format;
    this.size = size;
    this.isPublic = isPublic;
  }
}
