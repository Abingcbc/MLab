package com.mlab.controller;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mlab.domain.model.Graph;
import com.mlab.domain.model.Model;
import com.mlab.domain.TrainStatus;
import com.mlab.service.ModelService;
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
public class modelController {

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

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    public boolean train(@RequestParam(name = "id") int id){
        return modelService.pushIntoMq(id);
    }
    @RequestMapping(value = "/predict", method = RequestMethod.GET)
    public Boolean predict(@RequestParam(name = "id") int id){return false;}

}
