package org.sse.metadataservice.model;


import lombok.Data;

/**
 * @author cbc
 */
@Data
public class Model {

  private long modelId;
  private String username;
  private String modelName;
  private String description;
  private java.sql.Timestamp createTime;
  private long status;

}
