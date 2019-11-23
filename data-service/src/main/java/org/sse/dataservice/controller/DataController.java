package org.sse.dataservice.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sse.dataservice.service.DataService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author cbc
 */
@RestController
@RequestMapping(value = "/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public void createNewUserFolder(@RequestBody JSONObject jsonObject,
                                    HttpServletResponse response) {
        String username = jsonObject.getString("username");
        if (dataService.createNewUserFolder(username)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @RequestMapping(value = "/checkFile", method = RequestMethod.GET)
    public void checkIsFileExisted(@RequestBody JSONObject jsonObject,
                                   HttpServletResponse response) {
        String username = jsonObject.getString("username");
        String filename = jsonObject.getString("filename");
        int status = dataService.checkIsFileExisted(filename, username);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/checkChunk", method = RequestMethod.GET)
    public void checkIsChunkExisted(@RequestBody JSONObject jsonObject,
                                    HttpServletResponse response) {
        String username = jsonObject.getString("username");
        String filename = jsonObject.getString("filename");
        int chunkId = jsonObject.getInteger("chunkId");
        int status = dataService.checkIsChunkExisted(filename, username, chunkId);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestBody JSONObject jsonObject,
                           HttpServletResponse response) {
        String username = jsonObject.getString("username");
        String filename = jsonObject.getString("filename");
        int chunkId = jsonObject.getInteger("chunkId");
        if (dataService.saveChunk(file, filename, username, chunkId)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    public void mergeFile(@RequestBody JSONObject jsonObject,
                          HttpServletResponse response) {
        String username = jsonObject.getString("username");
        String filename = jsonObject.getString("filename");
        int chunks = jsonObject.getInteger("chunks");
        try {
            if (dataService.merge(username, filename, chunks)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestBody JSONObject jsonObject,
                             HttpServletResponse response) {
        String username = jsonObject.getString("username");
        String filename = jsonObject.getString("filename");
        response.reset();
        response.addHeader("Content-Disposition", filename);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // This method already close the outputStream
            dataService.download(username, filename, outputStream);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
