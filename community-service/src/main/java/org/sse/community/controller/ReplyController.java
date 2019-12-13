package org.sse.community.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.model.Reply;
import org.sse.community.service.ReplyService;

import java.util.List;

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

    @GetMapping("/get-replies-of-comment/{commentId}")
    public PageInfo<Reply> getRepliesOfComment(@PathVariable long commentId,
                                        @RequestParam("page-num") int pageNum,
                                        @RequestParam("page-size") int pageSize) {
        return replyService.getRepliesByCommentId(commentId,pageNum,pageSize);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteReply(@PathVariable long id){

            return replyService.deleteReply(id);
    }
}
