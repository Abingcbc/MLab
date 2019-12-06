package org.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.model.Like;
import org.sse.community.service.LikeService;

/**
 * @author HPY
 */
@RestController

public class LikeController {
    @Autowired
    LikeService likeService;

    @GetMapping("/like/post")
    public boolean likePost(@RequestParam(value = "name") String username,
                            @RequestParam(value = "post-id") long postId){
        return likeService.setLikeOnPost(username,postId);
    }

    @GetMapping("/unlike/post")
    public boolean unlikePost(@RequestParam(value = "name") String username,
                              @RequestParam(value = "post-id") long postId){
        return likeService.setUnlikeOnPost(username, postId);
    }

    @GetMapping("/like/comment")
    public boolean likeComment(@RequestParam(value = "name") String username,
                               @RequestParam(value = "comment-id") long commentId){
        return likeService.setLikeOnComment(username,commentId);
    }

    @GetMapping("/unlike/comment")
    public boolean unlikeComment(@RequestParam(value = "name") String username,
                                 @RequestParam(value = "comment-id") long commentId){
        return likeService.setUnlikeOnComment(username,commentId);
    }


}
