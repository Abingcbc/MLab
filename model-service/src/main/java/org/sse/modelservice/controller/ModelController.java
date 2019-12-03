package org.sse.modelservice.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.sse.modelservice.domain.model.Graph;
import org.sse.modelservice.service.ModelService;
import org.springframework.http.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version: V1.0
 * @author: cyy
 * @className: modelController
 * @packageName: com.mlab.lab.controller
 * @description: model Controller
 * @data: 2019-11-19 12:50
 **/

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Boolean generate(@RequestBody JSONObject jsonObject) {
        return modelService.setModel(jsonObject);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Graph view(@RequestParam(name = "name") String name) {
        return modelService.viewModel(name);
    }
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ResponseEntity<byte[]> download(@RequestBody JSONPObject jsonpObject) {
        return null;
    }



}
