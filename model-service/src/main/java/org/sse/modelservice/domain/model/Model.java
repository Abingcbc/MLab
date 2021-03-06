package org.sse.modelservice.domain.model;

import lombok.Data;

import java.util.*;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Model
 * @packageName: com.mlab.lab.entity
 * @description: model from json
 * @data: 2019-11-23 7:33
 **/

@Data
public class Model {
    private PipelineInformation pipelineInformation;
    private List<Node> nodeList;
    private List<Link> linkList;

    public Model(PipelineInformation pipelineInformation) {
        this.pipelineInformation = pipelineInformation;
        nodeList = new ArrayList<Node>();
        linkList = new ArrayList<Link>();
    }

    public Node getNodeByKey(int key) {
        for (Node e : nodeList) {
            if (e.getKey() == key) {
                return e;
            }
        }
        return null;
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public void setLink(int from, int to) {
        Node node1 = this.getNodeByKey(from);
        Node node2 = this.getNodeByKey(to);
        node1.addSucc(node2);
        node2.addPrev(node1);
        linkList.add(new Link(node1, node2));
    }

    public Boolean tpSort() {
        int count = nodeList.size();
        Queue<Node> queue = new LinkedList<Node>();
        List<Node> nodes = new ArrayList<Node>();
        for (Node n : nodeList) {
            if (n.getIndegree() == 0) {
                queue.offer(n);
            }

        }
        while (!queue.isEmpty()) {
            Node e = queue.poll();
            nodes.add(e);
            count--;
            for (Integer succKey : e.getSuccNodes()) {
                Node succ = this.getNodeByKey(succKey);
                succ.setIndegree(succ.getIndegree() - 1);
                if (succ.getIndegree() == 0) {
                    queue.offer(succ);
                }
            }
        }
        if (count != 0) {
            return false;
        }
        nodeList = nodes;
        for (Node n : nodeList) {
            n.setIndegree(n.getPrevNodes().size());
        }
        return true;
    }

}
