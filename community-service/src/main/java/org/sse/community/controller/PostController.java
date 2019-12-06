package org.sse.community.controller;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.model.Post;
import org.sse.community.service.PostService;

import java.util.List;

/**
 * @author HPY
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/{id}")
    public Post getPostByPostId(@PathVariable int id){
        return postService.getPostByPostId(id);
    }

    /**
     * get posts ordered by time
     * @param start the start pos
     * @param num the num of posts
     * @return list of post
     */
//    @RequestMapping("recent-post")
//    public List<Post> getPostOrderedByTime(@RequestParam int start, @RequestParam int num){
//        return postService.getPostOrderedByTime(start,num);
//    }

    /**
     * post a post with username,title,content
     * @param post post
     * @return is posted
     */
    @PostMapping("/publish")
    public boolean postAPost(@RequestBody Post post){
        return postService.postAPost(post) ;
    }

    /**
     * delete a post
     * @param postId postId
     * @return is executed
     */
    @GetMapping("/delete/{postId}")
    public boolean deleteAPost( @PathVariable int postId){
        return postService.deleteAPost(postId,1);
    }
}
