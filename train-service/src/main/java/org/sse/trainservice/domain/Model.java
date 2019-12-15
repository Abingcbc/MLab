package org.sse.trainservice.domain;


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
  public Model(String username, String modelName,String description){
    this.username=username;
    this.modelName=modelName;
    this.description=description;
  }
}
