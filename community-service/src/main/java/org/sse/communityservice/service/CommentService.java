package org.sse.communityservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sse.communityservice.dto.CommentDTO;
import org.sse.communityservice.mapper.*;
import org.sse.communityservice.model.Comment;

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
    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public long postComment(Comment comment){
        if(postMapper.getPostByPostId(comment.getPostId())==null){
            return 0;
        }
        else if(postMapper.checkStatus(comment.getPostId())==1) {
            return 0;
        }
        else {
            postMapper.addCommentNum(comment.getPostId());
            commentMapper.insertIntoComment(comment);
            long id = comment.getCommentId();
            return id;
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

    public PageInfo<CommentDTO> getCommentsByPostId(long postId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CommentDTO> commentDTOList = commentMapper.getCommentsByPostId(postId);
        for(CommentDTO c : commentDTOList) {
            String url = userMapper.getAvatarUrl(c.getUsername());
            c.setAvatarUrl(url);
        }
        return new PageInfo<>(commentDTOList);
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
