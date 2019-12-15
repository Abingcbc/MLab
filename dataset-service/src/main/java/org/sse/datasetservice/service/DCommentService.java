package org.sse.datasetservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.sse.datasetservice.client.UserServiceClient;
import org.sse.datasetservice.dto.DCommentDto;
import org.sse.datasetservice.mapper.DCommentMapper;
import org.sse.datasetservice.model.DComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ZTL
 */
@Service
@Slf4j
public class DCommentService {
    @Autowired
    DCommentMapper dCommentMapper;

    @Autowired
    UserServiceClient userServiceClient;

    public PageInfo<DCommentDto> getCommentByDatasetId(Long id, int pageNum, int pageSize){
        log.warn("---------");
        PageHelper.startPage(pageNum,pageSize);
        List<DCommentDto> dCommentList = dCommentMapper.getCommentByDatasetId(id);
        for (DCommentDto dComment : dCommentList) {
            dComment.setAvatarUrl(userServiceClient.getUserAvatarUrlByUsername(dComment.getUsername()));
        }
        log.warn("---------");
        return new PageInfo<>(dCommentList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long insertCommentByDatasetId(DComment dComment){
        dCommentMapper.insertCommentByDatasetId(dComment);
        return dComment.getDCommentId();
    }

    public int getCommentNumByDatasetId(Long datasetId) {
        return dCommentMapper.getCommentByDatasetId(datasetId).size();
    }
}
