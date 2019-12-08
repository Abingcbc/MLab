package org.sse.community.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.CommentMapper;
import org.sse.community.mapper.ReplyMapper;
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

    public PageInfo<Reply> getRepliesByCommentId(long commentId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Reply> list = replyMapper.getRepliesByCommentId(commentId);
        System.out.println(list.toString());
        PageInfo<Reply> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getList().toString());
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
