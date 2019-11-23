package org.sse.dataservice.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

/**
 * @author cbc
 */
@Service
public class DataService {

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

    public boolean createNewUserFolder(String username){
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            if (checkIsFileExisted(null, username) != 0) {
                return false;
            } else {
                fileSystem.mkdirs(new Path(folderPath+"/"+username));
                return true;
            }
        } catch (IOException e) {
            return false;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public int checkIsFileExisted(String filename, String username) {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            String filePath = folderPath+"/"+username;
            if (filename != null) {
                filePath += "/" + filename;
            }
            return fileSystem.exists(new Path(filePath)) ? 0 : 1;
        } catch (IOException e) {
            return -1;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public int checkIsChunkExisted(String filename, String username, int chunkId) {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            String filePath = folderPath+"/"+username+"/tmp/"+chunkId+filename;
            return fileSystem.exists(new Path(filePath)) ? 0 : 1;
        } catch (IOException e) {
            return -1;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public boolean saveChunk(MultipartFile multipartFile, String filename, String username, int chunkId) {
        FileSystem fileSystem = null;
        try {
            if (checkIsChunkExisted(filename, username, chunkId) == 0) {
                fileSystem = FileSystem.get(configuration);
                File file = multiPartFileToFile(multipartFile);
                Path srcPath = new Path(file.getPath());
                Path dstPath = new Path(folderPath+"/"+username+"/tmp/"+filename+"/"+chunkId+".tmp");
                fileSystem.copyFromLocalFile(srcPath, dstPath);
                file.delete();
            } else {
                return false;
            }
        } catch (IOException exception) {
            return false;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
        return true;
    }

    public boolean merge(String username, String filename, int chunks) {
        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        try {
            if (checkIsFileExisted(filename, username) == 0) {
                fileSystem = FileSystem.get(configuration);
                outputStream = fileSystem.create(
                        new Path(folderPath+"/"+username+"/"+filename), true);
                for (int i = 0; i < chunks; i++) {
                    Path tempPath = new Path(folderPath+"/"+username+"/tmp/"+chunks+filename);
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
            return false;
        } finally {
            IOUtils.closeStream(outputStream);
            closeFileSystemOrSteam(fileSystem);
        }
    }

    public int download(String username, String filename, OutputStream outputStream) {
        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
            if (checkIsFileExisted(filename, username) == 1) {
                fileSystem = FileSystem.get(configuration);
                inputStream = fileSystem.open(new Path(folderPath+"/"+username+"/"+filename));
                IOUtils.copyBytes(inputStream, outputStream, 1024, true);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return -1;
        } finally {
            closeFileSystemOrSteam(fileSystem);
        }
    }
}
