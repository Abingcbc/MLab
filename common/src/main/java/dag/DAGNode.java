package dag;

import lombok.Data;

/**
 * @author cbc
 */
@Data
public abstract class DAGNode {
    private String name;
    private NodeEnum type;
    private DAGNode[] preNodeList;
    private DAGNode[] nextNodeList;

    /**
     * 节点执行
     */
    public abstract void execute();
}
