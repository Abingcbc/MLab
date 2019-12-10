package org.sse.modelservice.domain.model;

import lombok.Data;

/**
 * @version: 1.0
 * @author: usr
 * @className: Response
 * @packageName: org.sse.modelservice.domain.model
 * @description: Response of client
 * @data: 2019-12-09 12:53
 **/
@Data
public class Response {
    private String msg;

    public Response(String msg) {
        this.msg = msg;
    }
}
