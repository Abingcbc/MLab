package org.sse.dataservice.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.sse.dataservice.client.MetadataServiceClient;
import org.sse.dataservice.model.Chunk;
import org.sse.dataservice.model.Dataset;
import org.sse.dataservice.model.FileInfo;
import org.sse.dataservice.model.Result;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author cbc
 */
@Service
public class DataService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MetadataServiceClient metadataServiceClient;

    @Value("${hdfs.folderPath}")
    private String folderPath;

    private Configuration configuration;

    DataService(Configuration configuration) {
        this.configuration = configuration;
    }

    private void closeFileSystemOrSteam(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File multiPartFileToFile(MultipartFile multipartFile) {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024*1024];
            int len = 0;
            while ((len=inputStream.read(buffer, 0, 1024*1024)) != -1) {
                outputStream.write(buffer);
            }
            return file;
        } catch (IOException e) {
            return file;
        } finally {
            closeFileSystemOrSteam(inputStream);
            closeFileSystemOrSteam(outputStream);
        }
    }

    public int checkIsFileExisted(String fileId, String format) {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            String filePath = folderPath+"/"+fileId+"."+format;
            return fileSystem.exists(new Path(filePath)) ? 0 : 1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public Boolean checkIsChunkExisted(Chunk chunk) {
        return redisTemplate.opsForSet().isMember(String.valueOf(chunk.getChunkNumber()), chunk.getIdentifier());
    }

    public boolean saveChunk(Chunk chunk) {
        FileSystem fileSystem = null;
        try {
            if (checkIsChunkExisted(chunk)) {
                fileSystem = FileSystem.get(configuration);
                File file = multiPartFileToFile(chunk.getFile());
                Path srcPath = new Path(file.getPath());
                Path dstPath = new Path(folderPath+"/tmp/"+chunk.getChunkNumber()+chunk.getFilename()+".tmp");
                fileSystem.copyFromLocalFile(srcPath, dstPath);
                redisTemplate.opsForSet().add(String.valueOf(chunk.getChunkNumber()), chunk.getIdentifier());
                redisTemplate.expire(String.valueOf(chunk.getChunkNumber()), 60, TimeUnit.MINUTES);
                return file.delete();
            } else {
                return false;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public boolean merge(FileInfo fileInfo) {
        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        ResponseEntity<Result> responseEntity = metadataServiceClient
                .createNewDataset(new Dataset(fileInfo.getUsername(),
                        fileInfo.getDatasetName(), fileInfo.getDescription(),
                        fileInfo.getFormat(), fileInfo.getSize(), fileInfo.getIsPublic()
                ));
        Result result = responseEntity.getBody();
        try {
            if (result == null) {
                return false;
            }
            if (checkIsFileExisted(result.getMsg(), fileInfo.getFormat()) == 0) {
                fileSystem = FileSystem.get(configuration);
                outputStream = fileSystem.create(
                        new Path(folderPath+"/"+result.getMsg()+"."+fileInfo.getFormat()), true);
                for (int i = 0; i < fileInfo.getTotalChunkNum(); i++) {
                    Path tempPath = new Path(folderPath+"/tmp/"+ i + result.getMsg() +".tmp");
                    FSDataInputStream inputStream = fileSystem.open(tempPath);
                    // Here we can't directly use `copyBytes` to close stream
                    // because we still need outputStream to be open
                    IOUtils.copyBytes(inputStream, outputStream, 1024, false);
                    IOUtils.closeStream(inputStream);
                    // The second parameter means recursively deleting
                    fileSystem.delete(tempPath, true);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            metadataServiceClient.deleteDataset(Long.valueOf(result.getMsg()));
            return false;
        } finally {
            IOUtils.closeStream(outputStream);
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public int download(String fileId, String format, OutputStream outputStream) {
        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
            if (checkIsFileExisted(fileId, format) == 1) {
                fileSystem = FileSystem.get(configuration);
                inputStream = fileSystem.open(new Path(folderPath+"/"+fileId+"."+format));
                IOUtils.copyBytes(inputStream, outputStream, 1024, true);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }
}
