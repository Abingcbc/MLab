package com.mlab.domain.model;

import com.mlab.domain.model.Node;
import lombok.Data;

/**
 * @version: V1.0
 * @author: cyy
 * @className: Link
 * @packageName: com.mlab.lab.entity
 * @description: line between nodes
 * @data: 2019-11-24 3:08
 **/
@Data
public class Link {
    private Node from;
    private Node to;

    public Link(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
}
