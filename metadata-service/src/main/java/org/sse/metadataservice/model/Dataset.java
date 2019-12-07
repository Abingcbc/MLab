package org.sse.metadataservice.model;


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

}
