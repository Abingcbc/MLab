package org.sse.datasetservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.sse.datasetservice.client.UserServiceClient;
import org.sse.datasetservice.dto.DReplyDto;
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
public class DReplyService {
    @Autowired
    DReplyMapper dReplyMapper;

    @Autowired
    UserServiceClient userServiceClient;

    public PageInfo<DReplyDto> getReplyByCommentId(Long id, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<DReply> dReplyList = dReplyMapper.getReplyByCommentId(id);
        List<DReplyDto> dReplyDtoList = new ArrayList<>();
        for (DReply dReply : dReplyList) {
            dReplyDtoList.add(new DReplyDto(dReply, userServiceClient.getUserAvatarUrlByUsername(dReply.getUsername())));
        }
        return new PageInfo<>(dReplyDtoList);
    }

    public boolean insertReplyByCommentId(String username,Long dCommentId,String content){
        return  dReplyMapper.insertReplyByCommentId(username,dCommentId,content);
    }
}
