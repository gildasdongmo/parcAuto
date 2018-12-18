/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.parcauto.doa.TypePanRepository;
import com.parcauto.entities.TypePanne;
import com.parcauto.entities.Utilisateur;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Uni2grow
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/tpannes")
public class TpanneRestController {

    @Autowired
    private TypePanRepository mr;

    public static final Logger logger = LoggerFactory.getLogger(TpanneRestController.class);

    // -------------------retourner toutes les tPannes -------------------------------------------
    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<TypePanne>> listAllTpannes() {
        List<TypePanne> tPannes = mr.findAll();
        if (tPannes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tPannes, HttpStatus.OK);
    }

    // -------------------retourner une seule TypePanne-------------------------------------------
    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<TypePanne> getTpanne(Long id) {
        logger.info("Fetching Tpanne with id {}", id);
        TypePanne tPanne = mr.findOne(id);
        if (tPanne == null) {
            logger.error("Tpanne with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Tpanne with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tPanne, HttpStatus.OK);
    }

    // -------------------ajouter une tPanne-------------------------------------------
    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> createTypePanne(@RequestBody TypePanne tPanne, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Tpanne : {}", tPanne);
        mr.save(tPanne);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/tPannes/get?id={id}").buildAndExpand(tPanne.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // -------------------enregistrement de la modification   ------------------------------------------------
    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> updateTpanne(@RequestBody TypePanne currentTpanne) {
        if (currentTpanne == null) {
            logger.error("Unable to update. TypePanne with id {} not found.", currentTpanne.getLibelle());
            return new ResponseEntity(new CustomErrorType("Unable to upate. Tpanne with id " + currentTpanne.getId()+ " not found."),
                    HttpStatus.NOT_FOUND);
        }
        mr.save(currentTpanne);
        return new ResponseEntity<>(currentTpanne, HttpStatus.OK);
    }

    // ------------------- Test Deletable-----------------------------------------
    @RequestMapping(value = "/test-deletable", method = GET)
    public ResponseEntity<TypePanne> deleteTest(Long id) {
        TypePanne tPanne = mr.findOne(id);
        logger.info("Fetching & Deleting TypePanne with id {}", id);
        if (tPanne == null) {
            logger.error("Unable to delete. TypePanne with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("impossible, tPanne avec l'id " + id + " Introuvable."),
                    HttpStatus.NOT_FOUND);
        }
        if(tPanne.getPannes().isEmpty()){
            mr.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity(new CustomErrorType("Impossible de suprimer. des pannes sont liés a ce type"),
                    HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Delete TypePanne-----------------------------------------
    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<TypePanne> deleteTypePanne(@RequestBody TypePanne tPanne) {
        Long id = tPanne.getId();
        logger.info("Fetching & Deleting TypePanne with id {}", id);
        if (tPanne == null) {
            logger.error("Unable to delete. TypePanne with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. TypePanne with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        mr.delete(tPanne);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
