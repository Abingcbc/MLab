package org.sse.datasetservice.controller;

import org.sse.datasetservice.model.DReply;
import org.sse.datasetservice.service.DReplyService;
import org.sse.datasetservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZTL
 */
@RestController
@RequestMapping("/dReply")
public class DReplyController {
    @Autowired
    DReplyService dReplyService;

    @PostMapping("/publish")
    Result insertReplyByDatasetId(@RequestBody DReply dReply){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dReplyService.insertReplyByCommentId(dReply.getUsername(),dReply.getDCommentId(),dReply.getContent()));
        return result;
    }

    @PostMapping("/getReply/{id}")
    Result<List> getReplyByDatasetId(@PathVariable("id")Long id){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dReplyService.getReplyByCommentId(id));
        return result;
    }
}
