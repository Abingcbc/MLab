package org.sse.communityservice.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.communityservice.dto.InputDTO;
import org.sse.communityservice.dto.PostDTO;
import org.sse.communityservice.model.Post;
import org.sse.communityservice.service.PostService;

/**
 * @author HPY
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/{id}")
    public PostDTO getPostByPostId(@PathVariable int id){
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

    @GetMapping("/get-order-by-time")
    public PageInfo<PostDTO> getPageOfPostsByTime(@RequestParam("page-num") int pageNum,
                                               @RequestParam("page-size") int pageSize){
        return postService.searchPostsOrderByTime(pageNum,pageSize,"");
    }

    @PostMapping("/search-order-by-time")
    public PageInfo<PostDTO> searchPostsOrderByTime(@RequestParam("page-num") int pageNum,
                                             @RequestParam("page-size") int pageSize,
                                             @RequestBody InputDTO inputDTO) {
        String keyword;
            keyword=inputDTO.getSearchInput();
//        }
        return postService.searchPostsOrderByTime(pageNum,pageSize,keyword);
    }


    @GetMapping("/get-order-by-like")
    public PageInfo<PostDTO> getPageOfPostsByLike(@RequestParam("page-num") int pageNum,
                                               @RequestParam("page-size") int pageSize){
        return postService.searchPostsOrderByLikeNum(pageNum,pageSize,"");
    }
    @PostMapping("/search-order-by-like")
    public PageInfo<PostDTO> searchPostsOrderByLike(@RequestParam("page-num") int pageNum,
                                                    @RequestParam("page-size") int pageSize,
                                                    @RequestBody InputDTO inputDTO) {
        String keyword;
//        if(inputDTO.isNull()){
//            keyword="";
//        }
//        else{
            keyword=inputDTO.getSearchInput();
//        }
        return postService.searchPostsOrderByLikeNum(pageNum,pageSize,keyword);
    }

    @GetMapping("/num")
    public int getNumOfPost(){
        return postService.getNumOfPost();
    }
}
