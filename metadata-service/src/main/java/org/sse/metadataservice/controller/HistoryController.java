package org.sse.metadataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.History;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.HistoryService;

import java.util.List;

/**
 * @author cbc
 */
@RestController
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @GetMapping(value = "/train/{username}")
    public List<History> getAllTrainByUsername(@PathVariable String username) {
        return historyService.getAllTrainByUsername(username);
    }

    @GetMapping(value = "/test/{username}")
    public List<History> getAllTestByUsername(@PathVariable String username) {
        return historyService.getAllTestByUsername(username);
    }

    @GetMapping(value = "/historyId/{historyId}")
    public History getDatasetById(@PathVariable Long historyId) {
        return historyService.getHistoryById(historyId);
    }

    @PostMapping(value = "/history")
    public long createNewHistory(@RequestBody History history) {
        Long historyId = historyService.createNewHistory(history);
        return  historyId;

    }

    @PostMapping(value = "/history_delete")
    public void deleteHistory(@RequestBody Long historyId) {
        historyService.deleteHistory(historyId);
    }

    @GetMapping(value = "/history_status/{historyId}/{status}")
    public void setHistory(@PathVariable Long historyId, @PathVariable Integer status) {
        historyService.setHistory(historyId,status);
    }

    @GetMapping(value = "/history/{historyId}")
    public void setEndTime(@PathVariable Long historyId){
        historyService.setEndTime(historyId);
    }

}
