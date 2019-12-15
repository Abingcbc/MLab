package org.sse.datasetservice.controller;

import com.github.pagehelper.PageInfo;
import org.sse.datasetservice.dto.DCommentDto;
import org.sse.datasetservice.model.DComment;
import org.sse.datasetservice.model.Result;
import org.sse.datasetservice.service.DCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZTL
 */
@RestController
public class DCommentController {
    @Autowired
    DCommentService dCommentService;

    @PostMapping("/publish")
    Result insertCommentByDatasetId(@RequestBody DComment comment){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dCommentService.insertCommentByDatasetId(comment));
        return result;
    }

    @GetMapping("/comment/{id}")
    PageInfo<DCommentDto> getCommentByDatasetId(@PathVariable Long id,
                                                @RequestParam("page-num") int pageNum,
                                                @RequestParam("page-size") int pageSize){
        return dCommentService.getCommentByDatasetId(id,pageNum,pageSize);
    }

    @GetMapping("/commentNum/{datasetId}")
    int getCommentNumByDatasetId(@PathVariable Long datasetId) {
        return dCommentService.getCommentNumByDatasetId(datasetId);
    }
}
