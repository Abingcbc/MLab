package org.sse.datasetservice.service;

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

    public List<DReplyDto> getReplyByCommentId(Long id){
        List<DReply> dReplyList = dReplyMapper.getReplyByCommentId(id);
        List<DReplyDto> dReplyDtoList = new ArrayList<>();
        for (DReply dReply : dReplyList) {
            dReplyDtoList.add(new DReplyDto(dReply, userServiceClient.getUserAvatarUrlByUsername(dReply.getUsername())));
        }
        return dReplyDtoList;
    }

    public boolean insertReplyByCommentId(String username,Long dCommentId,String content){
        return  dReplyMapper.insertReplyByCommentId(username,dCommentId,content);
    }
}
