package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.mapper.PostMapper;
import org.sse.community.mapper.UserMapper;
import org.sse.community.model.Post;

/**
 * @author HPY
 */
@Service
public class PostService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    CommentMapper commentMapper;


    /**
     * get post ordered by time
     *
     * @param start the start pos
     * @param num   the num of post
     * @return list of posts
     */
//    public List<Post> getPostOrderedByTime(int start, int num) {
//        return postMapper.getPostOrderedByTime(start, num);
//    }

    /**
     * post a post
     *
     * @param post post
     * @return is post
     */
    public boolean postAPost(Post post) {
        if(userMapper.getUserByUsername(post.getUsername())==null) {
            return false;
        }
        return postMapper.insertIntoPost(post.getUsername(), post.getTitle(), post.getContent());
    }

    /**
     * delete a post or undo delete
     *
     * @param postId postId
     * @param status is deleted, 0 means not deleted, 1 means deleted
     * @return is executed
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAPost(int postId, int status) {
        if (this.getPostByPostId(postId) == null) {
            return false;
        } else {
            likeMapper.setStatusByTypeAndTypeId(0,postId);
            commentMapper.setStatusByPostId(postId);
            return postMapper.updatePostStatus(postId, status);
        }


    }


    public Post getPostByPostId(long postId){
        Post post = postMapper.getPostByPostId(postId);
        if(post==null){
            return null;
        }
        else if(post.getStatus()==1) {
            return null;
        }
        else {
            return post;
        }
    }
}
