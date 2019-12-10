package org.sse.modelservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.sse.modelservice.domain.model.PipelineInformation;
import org.sse.modelservice.domain.nodeconfig.InputNodeConfig;
import org.sse.modelservice.repository.MongoDao;
import org.sse.modelservice.domain.model.Graph;
import org.sse.modelservice.domain.model.Model;
import org.sse.modelservice.domain.model.Node;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import org.sse.modelservice.client.DataServiceClient;
//import org.sse.modelservice.client.MedataServiceClient;

/**
 * @version: V1.0
 * @author: cyy
 * @className: ModelService
 * @packageName: com.mlab.lab.service
 * @description: model service
 * @data: 2019-11-19 12:47
 **/

@Service
public class ModelService {


    @Autowired
    MongoDao mongoDao;
    /**
    @Autowired
    DataServiceClient dataServiceClient;
    @Autowired
    MedataServiceClient medataServiceClient;
    **/

    public String setModel(JSONObject jsonObject,String username, String modelName, String description) {
        JSONArray nodeArray = jsonObject.getJSONArray("nodeDataArray");
        JSONArray linkArray = jsonObject.getJSONArray("linkDataArray");
        PipelineInformation pipelineInformation=new PipelineInformation(username,modelName,description);
        Model model = new Model(pipelineInformation);
        String inputFile=new String();
        for (int i = 0; i < nodeArray.size(); ++i) {
            Node node = new Node(nodeArray.getJSONObject(i).getInteger("key"));
            node.setConfig(nodeArray.getJSONObject(i));
            if(node.getConfig().getType()=="Input"){
                InputNodeConfig config=(InputNodeConfig) node.getConfig();
                inputFile=config.getFileName();
            }
            model.addNode(node);
        }
        for (int i = 0; i < linkArray.size(); ++i) {
            int from = linkArray.getJSONObject(i).getInteger("from");
            int to = linkArray.getJSONObject(i).getInteger("to");
            model.setLink(from, to);
        }
        if(!model.tpSort()){
            return "Circle Graph";
        }

        String result=generateSparkPipeline(model);
        mongoDao.save(new Graph(modelName,inputFile,jsonObject));
        return result;
    }



    public Graph editModel(String id) {
        return mongoDao.findByGraphId(id);
    }

    public Boolean deleteModel(int id){
        mongoDao.deleteById(id);
        return true;
    }

    public String generateSparkPipeline(Model model) {
        SparkSession spark=SparkSession.builder().appName(model.getPipelineInformation().getPipelineName()).master("local").getOrCreate();
        ArrayList<PipelineStage> pipelineStages = new ArrayList<PipelineStage>();
        for (Node node : model.getNodeList()) {
            if(node.getConfig().getType()=="Input"){
                continue;
            }
            pipelineStages.add(node.getConfig().getPipelineStage());
        }
        Pipeline pipeline = new Pipeline().setStages(pipelineStages.toArray(new PipelineStage[0]));
        try {
            pipeline.write().overwrite().save("pipeline/"+model.getPipelineInformation().getUsername()+"/"+model.getPipelineInformation().getPipelineName());
        }
        catch (Exception e){
            spark.stop();
            return e.getMessage();
        }

        spark.stop();
        return "OK";
    }

    public int download(String userId, String pipelineId, HttpServletResponse response){
        File src=new File("pipeline/"+userId+"/"+pipelineId);
        File zipDir=new File("tmp/"+pipelineId+".zip");
        try{
            FileOutputStream fos = new FileOutputStream(zipDir);
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressbyType(src, zos, baseDir);
            zos.close();
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {

                File file=new File("tmp/"+pipelineId+".zip");
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            }
            catch (Exception e) {
                e.printStackTrace();
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
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            return -1;
        }
        return 1;
    }

    private static void compressbyType(File src, ZipOutputStream zos,String baseDir) {

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
            compressbyType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }
}

