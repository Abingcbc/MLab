package org.sse.modelservice.controller;

import org.sse.modelservice.domain.model.Graph;
import org.sse.modelservice.service.ModelService;
import org.springframework.http.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

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

    @RequestMapping(value = "/generate/{username}/{pipelineName}", method = RequestMethod.POST)
    public String generate(@RequestBody JSONObject jsonObject,@PathVariable String username,@PathVariable String pipelineName,@RequestParam(name = "description") String description) {
        return modelService.setModel(jsonObject,username,pipelineName,description);
    }

    @RequestMapping(value = "/edit/{pipelineId}", method = RequestMethod.GET)
    public Graph view(@PathVariable String pipelineId) {
        return modelService.editModel(pipelineId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Boolean delete(@RequestParam(name="id") Integer id){return modelService.deleteModel(id);}

    @RequestMapping(value = "/download/{userId}/{pipelineId}.zip", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable String userId, @PathVariable String pipelineId) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            if (modelService.download(userId, pipelineId,response) == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
