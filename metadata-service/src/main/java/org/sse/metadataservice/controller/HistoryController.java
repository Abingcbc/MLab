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
    public ResponseEntity<Result> createNewHistory(@RequestBody History history) {
        Long historyId = historyService.createNewHistory(history);
        if (historyId > 0) {
            return new ResponseEntity<>(new Result(String.valueOf(historyId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/history_delete")
    public void deleteHistory(@RequestBody Long historyId) {
        historyService.deleteHistory(historyId);
    }

}
