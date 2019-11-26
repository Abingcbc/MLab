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
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping(value = "/newUser/{username}")
    public void createNewUserFolder(@PathVariable String username,
                                    HttpServletResponse response) {
        int status = dataService.createNewUserFolder(username);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/checkFile/{username}/{filename}")
    public void checkIsFileExisted(HttpServletResponse response,
                                   @PathVariable String username,
                                   @PathVariable String filename) {
        int status = dataService.checkIsFileExisted(filename, username);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/checkChunk/{username}/{filename}/{chunkId}")
    public void checkIsChunkExisted(HttpServletResponse response,
                                    @PathVariable String username,
                                    @PathVariable String filename,
                                    @PathVariable int chunkId) {
        int status = dataService.checkIsChunkExisted(filename, username, chunkId);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/upload/{username}/{filename}/{chunkId}")
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           HttpServletResponse response,
                           @PathVariable String username,
                           @PathVariable String filename,
                           @PathVariable int chunkId) {
        if (dataService.saveChunk(file, filename, username, chunkId)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @PostMapping(value = "/merge/{username}/{filename}/{chunks}")
    public void mergeFile(HttpServletResponse response,
                          @PathVariable String username,
                          @PathVariable String filename,
                          @PathVariable int chunks) {
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

    @GetMapping(value = "/download/{username}/{filename}")
    public void downloadFile(HttpServletResponse response,
                             @PathVariable String username,
                             @PathVariable String filename) {
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
