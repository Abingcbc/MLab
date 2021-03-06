package org.sse.metadataservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sse.metadataservice.model.Model;
import org.sse.metadataservice.model.Result;
import org.sse.metadataservice.service.ModelService;

import java.util.List;

/**
 * @author cbc
 */
@RestController
public class ModelController {


    @Autowired
    ModelService modelService;

    @GetMapping(value = "/model/{username}")
    public List<Model> getAllModelByUsername(@PathVariable String username) {
        return modelService.getAllModelByUsername(username);
    }

    @GetMapping(value = "/modelId/{modelId}")
    public Model getModelById(@PathVariable Long modelId) {
        return modelService.getModelById(modelId);
    }

    @PostMapping(value = "/model")
    public long createNewModel(@RequestBody Model Model) {
        Long ModelId = modelService.createNewModel(Model);
        return ModelId;
    }

    @PostMapping(value = "/model_delete")
    public void deleteModel(@RequestBody Long modelId) {
        modelService.deleteModel(modelId);
    }

}
