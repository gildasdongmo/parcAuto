/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.parcauto.doa.MarqueRepository;
import com.parcauto.entities.Marque;
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
@RequestMapping(value = "/marques")
public class MarquesRestController {

    @Autowired
    private MarqueRepository mr;

    public static final Logger logger = LoggerFactory.getLogger(MarquesRestController.class);

    // -------------------retourner toutes les marques -------------------------------------------
    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Marque>> listAllBrands() {
        List<Marque> marques = mr.findAll();
        if (marques.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Marque>>(marques, HttpStatus.OK);
    }

    // -------------------retourner une seule Marque-------------------------------------------
    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Marque> getBrand(Long id) {
        logger.info("Fetching Brand with id {}", id);
        Marque marque = mr.findOne(id);
        if (marque == null) {
            logger.error("Brand with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Brand with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Marque>(marque, HttpStatus.OK);
    }

    // -------------------ajouter une marque-------------------------------------------
    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> createMarque(@RequestBody Marque marque, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Brand : {}", marque);
        mr.save(marque);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/marques/get?id={id}").buildAndExpand(marque.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // -------------------enregistrement de la modification   ------------------------------------------------
    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> updateBrand(@RequestBody Marque currentBrand) {
        if (currentBrand == null) {
            logger.error("Unable to update. Marque with id {} not found.", currentBrand.getLibelle());
            return new ResponseEntity(new CustomErrorType("Unable to upate. Brand with id " + currentBrand.getId()+ " not found."),
                    HttpStatus.NOT_FOUND);
        }
        mr.save(currentBrand);
        return new ResponseEntity<Marque>(currentBrand, HttpStatus.OK);
    }

    // ------------------- Test Deletable-----------------------------------------
    @RequestMapping(value = "/test-deletable", method = GET)
    public ResponseEntity<Marque> deleteTest(Long id) {
        Marque marque = mr.findOne(id);
        logger.info("Fetching & Deleting Marque with id {}", id);
        if (marque == null) {
            logger.error("Unable to delete. Marque with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("impossible, marque avec l'id " + id + " Introuvable."),
                    HttpStatus.NOT_FOUND);
        }
        if(marque.getModels().isEmpty()){
            mr.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity(new CustomErrorType("Impossible de suprimer. des models sont liés a la marque"),
                    HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Delete Marque-----------------------------------------
    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Marque> deleteMarque(@RequestBody Marque marque) {
        Long id = marque.getId();
        logger.info("Fetching & Deleting Marque with id {}", id);
        if (marque == null) {
            logger.error("Unable to delete. Marque with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Marque with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        mr.delete(marque);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
