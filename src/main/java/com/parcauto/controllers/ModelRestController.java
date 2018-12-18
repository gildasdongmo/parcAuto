/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import static com.parcauto.controllers.MarquesRestController.logger;
import com.parcauto.doa.MarqueRepository;
import com.parcauto.doa.ModelRepository;
import com.parcauto.entities.Marque;
import com.parcauto.entities.Model;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Uni2grow
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/model")
public class ModelRestController {

    @Autowired
    private ModelRepository modeRep;
    @Autowired
    private MarqueRepository mr;
    public static final Logger logger = LoggerFactory.getLogger(ModelRestController.class);

    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Model>> listAllmodel() {
        List<Model> models = modeRep.findAll();
        if (models.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Model>>(models, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Model> getBrand(Long id) {
        logger.info("Fetching Model with id {}", id);
        Model model = modeRep.findOne(id);
        if (model == null) {
            logger.error("Model with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Model with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> createModel(@RequestBody Model model, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Model : {}", model);
        Model m = new Model(model.getLibelle(), mr.findOne(model.getBrand_id()));
        modeRep.save(m);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/model/get?id={id}").buildAndExpand(m.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> updateModel(@RequestBody Model currentModel) {
        if (currentModel == null) {
            logger.error("Unable to update. Model with id {} not found.", currentModel.getLibelle());
            return new ResponseEntity(new CustomErrorType("Unable to upate. Model with id " + currentModel.getId() + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        Model m = modeRep.findOne(currentModel.getId());
        m.setLibelle(currentModel.getLibelle());
        modeRep.save(m);
        return new ResponseEntity<Model>(m, HttpStatus.OK);
    }

    // ------------------- Test Deletable-----------------------------------------
    @RequestMapping(value = "/test-deletable", method = GET)
    public ResponseEntity<Model> deleteTest(Long id) {
        Model model = modeRep.findOne(id);
        logger.info("Fetching & Deleting Model with id {}", id);
        if (model == null) {
            logger.error("Unable to delete. Model with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("impossible, Model avec l'id " + id + " Introuvable."),
                    HttpStatus.NOT_FOUND);
        }
        if(model.getVehicules().isEmpty()){
            modeRep.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity(new CustomErrorType("Impossible de suprimer"),
                    HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Model> deleteModel(@RequestBody Model model) {
        Long id = model.getId();
        Model m = modeRep.findOne(id);
        logger.info("Fetching & Deleting model with id {}", id);
        if (m == null) {
            logger.error("Unable to delete. model with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. model with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        modeRep.delete(m);
        return new ResponseEntity<Model>(HttpStatus.NO_CONTENT);
    }

}
