package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.HistoryMapper;
import org.sse.metadataservice.model.History;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    public List<History> getAllTrainByUsername(String username) {
        return historyMapper.getAllTrainByUsername(username);
    }

    public List<History> getAllTestByUsername(String username) {
        return historyMapper.getAllTestByUsername(username);
    }

    public Long createNewHistory(History history) {
        if (historyMapper.createNewHistory(history) == 1) {
            return history.getModelId();
        } else {
            return (long) -1;
        }
    }

    public History getHistoryById(Long historyId) {
        return historyMapper.getHistoryById(historyId);
    }

    public void deleteHistory(Long historyId) {
        historyMapper.deleteHistoryById(historyId);
    }

    public void setHistory(Long historyId,Integer status){historyMapper.setHistoryById(historyId,status);}
}
