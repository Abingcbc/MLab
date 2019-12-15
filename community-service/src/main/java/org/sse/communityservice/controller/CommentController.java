package org.sse.communityservice.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.communityservice.dto.CommentDTO;
import org.sse.communityservice.model.Comment;
import org.sse.communityservice.service.CommentService;
import org.sse.communityservice.service.PostService;

/**
 * @author HPY
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @PostMapping("/publish")
    long postComment(@RequestBody Comment comment) {
        return commentService.postComment(comment);
    }

    @GetMapping("/{commentId}")
    Comment getCommentById(@PathVariable long commentId){

        return commentService.getCommentById(commentId);
    }

    @GetMapping("/delete/{commentId}")
    boolean deleteComment(@PathVariable long commentId) {

        return commentService.deleteComment(commentId);
    }
    @GetMapping("/get-comments-of-post/{postId}")
    PageInfo<CommentDTO> getCommentsOfPost(@PathVariable long postId,
                                           @RequestParam("page-num") int pageNum,
                                           @RequestParam("page-size") int pageSize) {
        return commentService.getCommentsByPostId(postId,pageNum,pageSize);
    }
}
