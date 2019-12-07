package org.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.dto.LikeDTO;
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

    @PostMapping("/like/post")
    public boolean likePost(@RequestBody LikeDTO likeDTO){
        return likeService.setLikeOnPost(likeDTO);
    }

    @PostMapping("/unlike/post")
    public boolean unlikePost(@RequestBody LikeDTO likeDTO){
        return likeService.setUnlikeOnPost(likeDTO);
    }

    @PostMapping("/like/comment")
    public boolean likeComment(@RequestBody LikeDTO likeDTO) {
        return likeService.setLikeOnComment(likeDTO);
    }
    @PostMapping("/unlike/comment")
    public boolean unlikeComment(@RequestBody LikeDTO likeDTO){
        return likeService.setUnlikeOnComment(likeDTO);
    }


}
