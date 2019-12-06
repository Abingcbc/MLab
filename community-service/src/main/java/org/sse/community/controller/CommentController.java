package org.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.model.Comment;
import org.sse.community.model.Post;
import org.sse.community.service.CommentService;
import org.sse.community.service.PostService;

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
    boolean postComment(@RequestBody Comment comment) {

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
}
