package org.sse.dataservice.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping(value = "/checkFile/{fileId}/{format}")
    public void checkIsFileExisted(HttpServletResponse response,
                                   @PathVariable String fileId,
                                   @PathVariable String format) {
        int status = dataService.checkIsFileExisted(fileId, format);
        if (status == 0) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 1){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/checkChunk/{fileId}/{chunkId}")
    public void checkIsChunkExisted(HttpServletResponse response,
                                    @PathVariable String fileId,
                                    @PathVariable int chunkId) {
        int status = dataService.checkIsChunkExisted(fileId, chunkId);
        if (status == 0) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 1){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/upload/{fileId}/{chunkId}")
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           HttpServletResponse response,
                           @PathVariable String fileId,
                           @PathVariable int chunkId) {
        if (dataService.saveChunk(file, fileId, chunkId)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @PostMapping(value = "/merge/{fileId}/{format}/{chunks}")
    public void mergeFile(HttpServletResponse response,
                          @PathVariable String fileId,
                          @PathVariable String format,
                          @PathVariable int chunks) {
        try {
            if (dataService.merge(fileId, format, chunks)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Provide data download function
     * Permission has been checked in authentication step
     */
    @GetMapping(value = "/download/{fileId}/{format}")
    public void downloadFile(HttpServletResponse response,
                             @PathVariable String fileId,
                             @PathVariable String format) {
        response.reset();
        response.addHeader("Content-Disposition", fileId+"."+format);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // This method already close the outputStream
            if (dataService.download(fileId, format, outputStream) == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
