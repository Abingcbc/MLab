package org.sse.dataservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.sse.dataservice.client.MetadataServiceClient;
import org.sse.dataservice.model.Chunk;
import org.sse.dataservice.model.Dataset;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author cbc
 */
@Slf4j
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

    private void closeFileSystemOrStream(Closeable closeable) {
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
            closeFileSystemOrStream(inputStream);
            closeFileSystemOrStream(outputStream);
        }
    }

    public int checkIsFileExisted(String fileId, String format) {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            String filePath = folderPath+"/"+fileId+"."+format;
            return fileSystem.exists(new Path(filePath)) ? 1 : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeFileSystemOrStream(fileSystem);
        }
    }

    public Boolean checkIsChunkExisted(Chunk chunk) {
        return redisTemplate.opsForSet().isMember(String.valueOf(chunk.getIdentifier()), String.valueOf(chunk.getChunkNumber()));
    }

    public int saveChunk(Chunk chunk) {
        FileSystem fileSystem = null;
        try {
            if (!checkIsChunkExisted(chunk)) {
                fileSystem = FileSystem.get(configuration);
                File file = multiPartFileToFile(chunk.getFile());
                Path srcPath = new Path(file.getPath());
                Path dstPath = new Path(folderPath+"/tmp/"+chunk.getChunkNumber()+chunk.getIdentifier()+".tmp");
                fileSystem.copyFromLocalFile(srcPath, dstPath);
                redisTemplate.opsForSet().add(String.valueOf(chunk.getIdentifier()), String.valueOf(chunk.getChunkNumber()));
                redisTemplate.expire(String.valueOf(chunk.getIdentifier()), 60, TimeUnit.MINUTES);
                return 1;
            } else {
                return 0;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return -1;
        } finally {
            closeFileSystemOrStream(fileSystem);
        }
    }

    public int merge(Long datasetId, Long totalChunkNum) {
        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        Dataset dataset = metadataServiceClient.getDatasetById(datasetId);
        try {
            if (dataset == null) {
                return -1;
            }
            if (checkIsFileExisted(String.valueOf(datasetId), dataset.getFormat()) == 0) {
                fileSystem = FileSystem.get(configuration);
                outputStream = fileSystem.create(
                        new Path(folderPath+"/"+String.valueOf(datasetId)+"."+dataset.getFormat()), true);
                Long totalNum = redisTemplate.opsForSet().size(String.valueOf(datasetId));
                if (!totalChunkNum.equals(totalNum)) {
                    return -1;
                }
                for (int i = 1; i <= totalNum; i++) {
                    Path tempPath = new Path(folderPath+"/tmp/"+ i + datasetId +".tmp");
                    FSDataInputStream inputStream = fileSystem.open(tempPath);
                    // Here we can't directly use `copyBytes` to close stream
                    // because we still need outputStream to be open
                    IOUtils.copyBytes(inputStream, outputStream, 1024, false);
                    IOUtils.closeStream(inputStream);
                    // The second parameter means recursively deleting
                    fileSystem.delete(tempPath, true);
                }
                metadataServiceClient.enableDataset(datasetId);
                return 1;
            } else {
                return -2;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return -3;
        } finally {
            IOUtils.closeStream(outputStream);
            closeFileSystemOrStream(fileSystem);
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
            closeFileSystemOrStream(fileSystem);
        }
    }
}
