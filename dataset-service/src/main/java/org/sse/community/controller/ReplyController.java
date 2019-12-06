package org.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.model.Reply;
import org.sse.community.service.ReplyService;

/**
 * @author HPY
 */
@RestController
@RequestMapping("reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @PostMapping("/publish")
    public boolean postReply(@RequestBody Reply reply){
        return replyService.insertIntoReply(reply);
    }

    @GetMapping("/{id}")
    public Reply getReplyByReplyId(@PathVariable long id){
        return replyService.getReplyByReplyId(id);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteReply(@PathVariable long id){

            return replyService.deleteReply(id);
    }
}
