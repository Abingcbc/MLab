package org.sse.modelservice.controller;

import org.sse.modelservice.domain.model.Graph;
import org.sse.modelservice.service.DownloadService;
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

    @Autowired
    DownloadService downloadService;

    @RequestMapping(value = "/generate/{username}/{pipelineName}", method = RequestMethod.POST)
    public String generate(@RequestBody JSONObject jsonObject,@PathVariable String username,@PathVariable String pipelineName,@RequestParam(name = "description") String description) {
        return modelService.setModel(jsonObject,username,pipelineName,description);
    }

    @RequestMapping(value = "/edit/{pipelineId}", method = RequestMethod.GET)
    public Graph edit(@PathVariable String pipelineId) {
        return modelService.editPipeline(pipelineId);
    }

    @RequestMapping(value = "/delete/{userId}/{fileType}/{fileId}", method = RequestMethod.GET)
    public int delete(@PathVariable long userId,@PathVariable String fileType,@PathVariable long fileId){return modelService.deleteFile(userId,fileType,fileId);}

    @RequestMapping(value = "/download/{username}/{fileType}/{filename}.{format}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable String username, @PathVariable String fileType,@PathVariable String filename,@PathVariable String format) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            if (downloadService.download(username,fileType, filename,format,response) == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
