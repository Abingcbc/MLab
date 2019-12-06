package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.mapper.PostMapper;
import org.sse.community.model.Comment;
import org.sse.community.model.Like;
import org.sse.community.model.Post;

/**
 * @author HPY
 */
@Service
public class LikeService {
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentMapper commentMapper;

    /**
     * set like on post
     * @param username username
     * @param postId post/comment id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setLikeOnPost(String username,long postId) {
        Post post = postMapper.getPostByPostId(postId);
        if(post==null||post.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,0,postId);
        if(like==null) {
            postMapper.addLikeNum(postId);
            return likeMapper.insertIntoLike(postId, 0,username,1);
        }
        else if(like.getStatus()==0){
            postMapper.addLikeNum(postId);
            return likeMapper.setStatusAndCreateTime(like.getLikeId());
        }
        else {
            return false;
        }

    }

    /**
     * set unlike on Post
     * @param username username
     * @param postId post id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setUnlikeOnPost(String username,long postId) {
        Post post = postMapper.getPostByPostId(postId);
        if(post==null||post.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,0,postId);
        if(like!=null&&like.getStatus()==1){
            postMapper.reduceLikeNum(postId);
            return likeMapper.setStatus(0,like.getLikeId());
        }
        else {
            return false;
        }

    }
    /**
     * set like on comment
     * @param username username
     * @param commentId comment id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setLikeOnComment(String username,long commentId){
        Comment comment = commentMapper.getCommentById(commentId);
        if(comment==null||comment.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,1,commentId);
        if(like==null) {
            commentMapper.addLikeNum(commentId);
            return likeMapper.insertIntoLike(commentId, 1,username,1);
        }
        else if(like.getStatus()==0){
            commentMapper.addLikeNum(commentId);
            return likeMapper.setStatusAndCreateTime(like.getLikeId());
        }
        else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean setUnlikeOnComment(String username,long commentId) {
        Comment comment=commentMapper.getCommentById(commentId);
        if(comment==null||comment.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,1,commentId);
        if(like!=null&&like.getStatus()==1){
            commentMapper.reduceLikeNum(commentId);
            return likeMapper.setStatus(0,like.getLikeId());
        }
        else {
            return false;
        }

    }

}
