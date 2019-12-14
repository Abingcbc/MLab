package org.sse.community.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.dto.ReplyDTO;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.ReplyMapper;
import org.sse.community.mapper.UserMapper;
import org.sse.community.model.Comment;
import org.sse.community.model.Reply;

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
