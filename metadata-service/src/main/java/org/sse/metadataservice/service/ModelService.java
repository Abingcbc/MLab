package org.sse.metadataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sse.metadataservice.mapper.ModelMapper;
import org.sse.metadataservice.model.Model;

import java.util.List;

/**
 * @author cbc
 */
@Service
public class ModelService {

    @Autowired
    ModelMapper modelMapper;

    public List<Model> getAllModelByUsername(String username) {
        return modelMapper.getAllModelByUsername(username);
    }

    public Long createNewModel(Model model) {
        if (modelMapper.createNewModel(model) == 1) {
            return model.getModelId();
        } else {
            return (long) -1;
        }
    }

    public Model getModelById(Long modelId) {
        return modelMapper.getModelById(modelId);
    }

    public void deleteModel(Long modelId) {
        modelMapper.deleteModelById(modelId);
    }

}
