package org.sse.communityservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sse.communityservice.dto.ReplyDTO;
import org.sse.communityservice.mapper.CommentMapper;
import org.sse.communityservice.mapper.ReplyMapper;
import org.sse.communityservice.mapper.UserMapper;
import org.sse.communityservice.model.Comment;
import org.sse.communityservice.model.Reply;

import java.util.List;

/**
 * @author HPY
 */
@Service
public class ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean insertIntoReply(Reply reply){
        Comment comment=commentMapper.getCommentById(reply.getCommentId());
        if(comment==null){
            return false;
        }
        else if (comment.getStatus()==1){
            return false;
        }
        else {
            commentMapper.addReplyNum(comment.getCommentId());
            return replyMapper.insertIntoReply(reply.getUsername(),reply.getCommentId(),reply.getContent());
        }
    }

    public Reply getReplyByReplyId(long id) {
        Reply reply=replyMapper.getReplyByReplyId(id);
        if(reply==null){
            return null;
        }
        else if (reply.getStatus()==1){
            return null;
        }
        else {
            return replyMapper.getReplyByReplyId(id);
        }
    }

    public PageInfo<ReplyDTO> getRepliesByCommentId(long commentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ReplyDTO> list = replyMapper.getRepliesByCommentId(commentId);
        for(ReplyDTO c : list) {
            String url = userMapper.getAvatarUrl(c.getUsername());
            c.setAvatarUrl(url);
        }
        PageInfo<ReplyDTO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReply(long id) {
        Reply reply=replyMapper.getReplyByReplyId(id);
        if(reply==null){
            return false;
        }
        else if (reply.getStatus()==1) {
            return false;
        }
        else {
            commentMapper.reduceReplyNum(reply.getCommentId());
            return replyMapper.setStatus(id);
        }
    }
}
