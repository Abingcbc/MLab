package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.LikeMapper;
import org.sse.community.mapper.PostMapper;
import org.sse.community.mapper.ReplyMapper;
import org.sse.community.model.Comment;
import org.sse.community.model.Post;

import java.util.List;

/**
 * @author HPY
 */
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    ReplyMapper replyMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean postComment(Comment comment){
        if(postMapper.getPostByPostId(comment.getPostId())==null){
            return false;
        }
        else if(postMapper.checkStatus(comment.getPostId())==1) {
            return false;
        }
        else {
            postMapper.addCommentNum(comment.getPostId());
            return commentMapper.insertIntoComment(comment.getPostId(),comment.getUsername(),comment.getContent());
        }
    }

    public Comment getCommentById(long commentId) {
        Comment comment = commentMapper.getCommentById(commentId);
        if(comment == null) {
            return null;
        }
        else if (comment.getStatus()==1){
            return null;
        }
        else {
            return commentMapper.getCommentById(commentId);
        }
    }

    public List<Comment> getCommentsByPostId(long postId,int pageNum,int commentNum) {
        int start=pageNum*commentNum-commentNum;

        return commentMapper.getCommentsByPostId(postId,start,commentNum);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(long commentId) {
        Comment comment=commentMapper.getCommentById(commentId);
        if(comment==null){
            return false;
        }
        else if(comment.getStatus()==1) {
            return false;
        }
        else {
            postMapper.reduceCommentNum(comment.getPostId());
            likeMapper.setStatusByTypeAndTypeId(1,commentId);
            replyMapper.setStatusByCommentId(commentId);
            return commentMapper.setStatus(commentId);
        }
    }
}
