package org.sse.metadataservice.model;


import lombok.Data;

/**
 * @author cbc
 */
@Data
public class History {

  private long historyId;
  private long runType;
  private String username;
  private long pipelineId;
  private long modelId;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private long status;

}
