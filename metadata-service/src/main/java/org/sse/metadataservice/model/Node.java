package org.sse.metadataservice.model;


import lombok.Data;

/**
 * @author cbc
 */
@Data
public class Node {

  private long nodeId;
  private String username;
  private String name;
  private java.sql.Timestamp createTime;
  private long status;

}
