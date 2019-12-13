package org.sse.dataservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sse.dataservice.model.Chunk;
import org.sse.dataservice.model.FileInfo;
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

    @GetMapping(value = "/chunk")
    public void checkIsChunkExisted(@RequestParam(value = "datasetId") Long datasetId,
                                    @RequestParam(value = "identifier") String identifier,
                                    HttpServletResponse response) {
        Boolean status = dataService.checkIsChunkExisted(datasetId, identifier);
        if (status) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/chunk")
    public void uploadFile(Chunk chunk, HttpServletResponse response) {
        int status = dataService.saveChunk(chunk);
        if (status == 1) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/merge")
    public void mergeFile(@RequestParam(value = "datasetId") Long datasetId,
                          HttpServletResponse response) {
        try {
            int status = dataService.merge(datasetId);
            switch (status) {
                case 1:
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case -1:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                case -2:
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    break;
                case -3:
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    break;
                default:
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
