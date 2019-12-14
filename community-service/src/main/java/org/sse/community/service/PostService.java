package org.sse.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.mapper.PostMapper;
import org.sse.community.mapper.UserMapper;
import org.sse.community.model.Post;

import java.util.List;

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


    /**
     * get Post by id
     * @param postId post id
     * @return post
     */
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

    public PageInfo<Post> searchPostsOrderByTime(int pageNum, int pageSize, String keyword){
        keyword = "%"+keyword+"%";
        PageHelper.startPage(pageNum,pageSize);
        List<Post> list=postMapper.searchPostsOrderByTime(keyword);
        return new PageInfo<>(list);
    }

    public int getNumOfPost() {
        return postMapper.selectCount();
    }

    public PageInfo<Post> searchPostsOrderByLikeNum(int pageNum,int pageSize,String keyword){
        keyword = "%"+keyword+"%";
        PageHelper.startPage(pageNum,pageSize);
        List<Post> list = postMapper.searchPostsOrderByLikeNum(keyword);
        return new PageInfo<>(list);
    }
}
