package org.sse.datasetservice.controller;

import org.sse.datasetservice.model.DComment;
import org.sse.datasetservice.service.DCommentService;
import org.sse.datasetservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZTL
 */
@RestController
@RequestMapping("/dComment")
public class DCommentController {
    @Autowired
    DCommentService dCommentService;

    @PostMapping("/publish")
    Result insertCommentByDatasetId(@RequestBody DComment comment){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dCommentService.insertCommentByDatasetId(comment.getDatasetId(),comment.getUsername(),comment.getContent()));
        return result;
    }

    @PostMapping("/getComment/{id}")
    Result<List> getCommentByDatasetId(@PathVariable("id")Long id){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dCommentService.getCommentByDatasetId(id));
        return result;
    }
}
