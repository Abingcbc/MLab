package org.sse.metadataservice.model;


import lombok.Data;

/**
 * @author cbc
 */
@Data
public class Pipeline {

  private long pipelineId;
  private String username;
  private String pipelineName;
  private String description;
  private java.sql.Timestamp createTime;
  private long inputFile;
  private long status;

}
