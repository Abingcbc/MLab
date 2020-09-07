package org.mlab.data.domain.oss.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.mlab.data.domain.oss.OSSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import oss.FileTypeEnum;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cbc
 */
@Service
public class MinIOService implements OSSService {

    @Value("${minio.server}")
    private String minioServer;

    @Value("${minio.accessKey}")
    private String minioAccessKey;

    @Value("${minio.secretKey}")
    private String minioSecretKey;

    private MinioClient client;

    public MinIOService() {
        minioServer = "http://localhost:9000";
        minioAccessKey = "mlab";
        minioSecretKey = "87270099";
        client = MinioClient.builder().endpoint(minioServer)
                .credentials(minioAccessKey, minioSecretKey).build();
        try {
            checkBucketValid();
        } catch (Exception e) {
            // not provide service if bucket cannot be checked
            client = null;
            e.printStackTrace();
        }
    }

    @Override
    public boolean isExist(String path, FileTypeEnum fileType) {
        try {
            ObjectStat stat = client.statObject(StatObjectArgs.builder()
                    .bucket(fileType.name()).object(path).build());
            return null != stat;
        } catch (Exception e) {
            if (!(e instanceof ErrorResponseException) ||
                    !"NO_SUCH_KEY".equals(((ErrorResponseException) e).errorResponse().errorCode()
                            .name())) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public String save(byte[] object, String path, FileTypeEnum fileType) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(object);
            client.putObject(PutObjectArgs.builder().bucket(fileType.name())
                    .object(path).stream(inputStream, inputStream.available(), -1).build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public byte[] get(String path, FileTypeEnum fileType) {
        try {
            InputStream inputStream = client.getObject(GetObjectArgs.builder()
                    .bucket(fileType.name()).object(path).build());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean remove(String path, FileTypeEnum fileType) {
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(fileType.name()).object(path).build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void checkBucketValid() throws Exception {
        if (null == client) {
            throw new Exception("MinIO client not init");
        }
        try {
            List<String> bucketList = client.listBuckets().stream()
                    .map(Bucket::name).collect(Collectors.toList());
            for (FileTypeEnum fileType : FileTypeEnum.values()) {
                if (!bucketList.contains(fileType.name())) {
                    client.makeBucket(MakeBucketArgs.builder()
                            .bucket(fileType.name()).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("MinIO bucket check failed!");
        }
    }
}
