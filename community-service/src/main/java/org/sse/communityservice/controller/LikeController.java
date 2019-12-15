package org.sse.communityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.communityservice.dto.LikeDTO;
import org.sse.communityservice.service.LikeService;

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

    @GetMapping("/like/check")
    public boolean checkLike(@RequestParam("user") String username,@RequestParam("type") long type,@RequestParam("type-id") long typeId){
        return likeService.checkLike(username,type,typeId);
    }


}
