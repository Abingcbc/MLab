package org.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.community.mapper.HistoryMapper;


/**
 * @author HPY
 */
@Service
public class HistoryService {
    @Autowired
    HistoryMapper historyMapper;
}
