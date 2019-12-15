package org.sse.modelservice.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @version: 1.0
 * @author: usr
 * @className: DownloadComponent
 * @packageName: org.sse.modelservice.service
 * @description: download component
 * @data: 2019-12-11 12:15
 **/

@Service
public class DownloadService {

    public int download(String username,String fileType, String filename,String format, HttpServletResponse response){
        File src=new File(fileType+"/"+username+"/"+filename);
        File zipDir=new File("tmp/"+filename+"."+"zip");
        try{
            FileOutputStream fos = new FileOutputStream(zipDir);
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressByType(src, zos, baseDir);
            zos.close();
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {

                File file=new File("tmp/"+filename+".zip");
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
            finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        return -1;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
    private static void compressByType(File src, ZipOutputStream zos, String baseDir) {

        if (!src.exists()) {
            return;
        }
        if (src.isFile()) {
            compressFile(src, zos, baseDir);

        } else if (src.isDirectory()) {
            compressDir(src, zos, baseDir);
        }

    }

    private static void compressFile(File file, ZipOutputStream zos,String baseDir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zos.putNextEntry(entry);
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
            bis.close();

        } catch (Exception e) {

        }
    }

    private static void compressDir(File dir, ZipOutputStream zos,String baseDir) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if(files.length == 0){
            try {
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName()+File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
            compressByType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }
}
