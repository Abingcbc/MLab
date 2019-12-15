package org.sse.modelservice.service;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @version: 1.0
 * @author: usr
 * @className: DeleteHandler
 * @packageName: org.sse.modelservice.service
 * @description: delete file
 * @data: 2019-12-11 19:35
 **/
@Component
public class DeleteHandler {
    private Boolean flag;
    private File file;

    public boolean deleteFile(String sPath) {
        flag = false;
        file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public boolean deleteDirectory(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag){ break;}
            }
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag){ break;}
            }
        }
        if (!flag){ return false;}
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
