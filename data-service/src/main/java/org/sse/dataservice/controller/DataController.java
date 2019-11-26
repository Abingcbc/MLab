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

    @PostMapping(value = "/newUser")
    public void createNewUserFolder(@RequestBody JSONObject jsonObject,
                                    HttpServletResponse response) {
        String username = jsonObject.getString("username");
        int status = dataService.createNewUserFolder(username);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/checkFile")
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

    @GetMapping(value = "/checkChunk")
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

    @PostMapping(value = "/upload")
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

    @PostMapping(value = "/merge")
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

    @GetMapping(value = "/download")
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
