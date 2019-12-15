package org.sse.datasetservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.sse.datasetservice.client.UserServiceClient;
import org.sse.datasetservice.dto.DReplyDto;
import org.sse.datasetservice.mapper.DCommentMapper;
import org.sse.datasetservice.mapper.DReplyMapper;
import org.sse.datasetservice.model.DReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZTL
 */
@Service
@Slf4j
public class DReplyService {
    @Autowired
    DReplyMapper dReplyMapper;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    DCommentMapper dCommentMapper;

    public PageInfo<DReplyDto> getReplyByCommentId(Long id, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DReplyDto> dReplyList = dReplyMapper.getReplyByCommentId(id);
        for (DReplyDto dReply : dReplyList) {
            dReply.setAvatarUrl(userServiceClient.getUserAvatarUrlByUsername(dReply.getUsername()));
        }
        return new PageInfo<>(dReplyList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long insertReplyByCommentId(DReply dReply){
        log.warn(dReply.toString());
        dCommentMapper.updateDCommentReplyNum(dReply.getDCommentId());
        dReplyMapper.insertReplyByCommentId(dReply);
        return  dReply.getDReplyId();
    }
}
