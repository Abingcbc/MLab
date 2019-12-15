package org.sse.datasetservice.controller;

import com.github.pagehelper.PageInfo;
import org.sse.datasetservice.dto.DReplyDto;
import org.sse.datasetservice.model.DReply;
import org.sse.datasetservice.model.Result;
import org.sse.datasetservice.service.DReplyService;
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
    Result insertReplyByCommentId(@RequestBody DReply dReply){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(dReplyService.insertReplyByCommentId(dReply));
        return result;
    }

    @GetMapping("/reply/{id}")
    PageInfo<DReplyDto> getReplyByCommentId(@PathVariable Long id,
                                            @RequestParam("page-num") int pageNum,
                                            @RequestParam("page-size") int pageSize ){
        return dReplyService.getReplyByCommentId(id,pageNum,pageSize);
    }
}
