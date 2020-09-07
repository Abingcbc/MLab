package dag;

/**
 * @author cbc
 */

public enum NodeEnum {
    /**
     * 普通DAG图节点
     */
    NORMAL,
    /**
     * 直接运行Jupyter
     */
    JUPYTER,
    /**
     * 运行Flink jar包
     */
    FLINK
}
