package org.sse.datasetservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class DCommentService {
    @Autowired
    DCommentMapper dCommentMapper;

    @Autowired
    UserServiceClient userServiceClient;

    public PageInfo<DCommentDto> getCommentByDatasetId(Long id, int pageNum, int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<DComment> dCommentList = dCommentMapper.getCommentByDatasetId(id);
        List<DCommentDto> dCommentDtoList = new ArrayList<>();
        for (DComment dComment : dCommentList) {
            dCommentDtoList.add(new DCommentDto(dComment, userServiceClient.getUserAvatarUrlByUsername(dComment.getUsername())));
        }
        return new PageInfo<>(dCommentDtoList);
    }
    public boolean insertCommentByDatasetId(Long id,String username,String content){
        return dCommentMapper.insertCommentByDatasetId(id,username,content);
    }

    public int getCommentNumByDatasetId(Long datasetId) {
        return dCommentMapper.getCommentByDatasetId(datasetId).size();
    }
}
