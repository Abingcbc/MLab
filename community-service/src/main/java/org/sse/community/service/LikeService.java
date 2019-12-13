package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sse.community.dto.LikeDTO;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.mapper.PostMapper;
import org.sse.community.mapper.UserMapper;
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
    @Autowired
    UserMapper userMapper;

    /**
     * set like on post
     * @param likeDTO contain username,liked username,type,type id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setLikeOnPost(LikeDTO likeDTO) {
        Post post = postMapper.getPostByPostId(likeDTO.getTypeId());
        String username = likeDTO.getUsername();
        String likedUsername = likeDTO.getLikedUsername();
        long type=likeDTO.getType();
        long postId=likeDTO.getTypeId();

        if(post==null||post.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,0,postId);
        if(like==null) {
            postMapper.addLikeNum(postId);
            userMapper.addLikeNum(likedUsername);
            return likeMapper.insertIntoLike(postId, 0,username,1);
        }
        else if(like.getStatus()==0){
            postMapper.addLikeNum(postId);
            userMapper.addLikeNum(likedUsername);
            return likeMapper.setStatusAndCreateTime(like.getLikeId());
        }
        else {
            return false;
        }

    }

    /**
     * set unlike on Post
     * @param likeDTO contain username,liked username,type,type id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setUnlikeOnPost(LikeDTO likeDTO) {
        String username = likeDTO.getUsername();
        String likedUsername = likeDTO.getLikedUsername();
        long postId = likeDTO.getTypeId();
        long type = likeDTO.getType();

        Post post = postMapper.getPostByPostId(postId);
        if(post==null||post.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,0,postId);
        if(like!=null&&like.getStatus()==1){
            postMapper.reduceLikeNum(postId);
            userMapper.reduceLikeNum(likedUsername);
            return likeMapper.setStatus(0,like.getLikeId());
        }
        else {
            return false;
        }

    }
    /**
     * set like on comment
     * @param likeDTO contain username,liked username,type,type id
     * @return is set
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setLikeOnComment(LikeDTO likeDTO){
        String username=likeDTO.getUsername();
        String likedUsername=likeDTO.getLikedUsername();
        long commentId=likeDTO.getTypeId();
        long type = likeDTO.getType();


        Comment comment = commentMapper.getCommentById(commentId);
        if(comment==null||comment.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,1,commentId);
        if(like==null) {
            commentMapper.addLikeNum(commentId);
            userMapper.addLikeNum(likedUsername);
            return likeMapper.insertIntoLike(commentId, 1,username,1);
        }
        else if(like.getStatus()==0){
            commentMapper.addLikeNum(commentId);
            userMapper.addLikeNum(likedUsername);
            return likeMapper.setStatusAndCreateTime(like.getLikeId());
        }
        else {
            return false;
        }
    }

    /**
     *set unlike on comment
     * @param likeDTO contain username,liked username,type,type id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setUnlikeOnComment(LikeDTO likeDTO) {
        String username=likeDTO.getUsername();
        String likedUsername=likeDTO.getLikedUsername();
        long type = likeDTO.getType();
        long commentId = likeDTO.getTypeId();


        Comment comment=commentMapper.getCommentById(commentId);
        if(comment==null||comment.getStatus()!=0){
            return false;
        }
        Like like=likeMapper.getLikeByNameAndType(username,1,commentId);
        if(like!=null&&like.getStatus()==1){
            commentMapper.reduceLikeNum(commentId);
            userMapper.reduceLikeNum(likedUsername);
            return likeMapper.setStatus(0,like.getLikeId());
        }
        else {
            return false;
        }

    }

    public boolean checkLike(String username, long type, long typeId) {
        Like like = likeMapper.getLikeByNameAndType(username,type,typeId);
        if(like!=null && like.getStatus()==1) {
            return true;
        }
        else {
            return false;
        }
    }

}
