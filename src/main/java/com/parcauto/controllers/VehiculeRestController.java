/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import com.parcauto.doa.MarqueRepository;
import com.parcauto.doa.ModelRepository;
import com.parcauto.doa.PersonneRepository;
import com.parcauto.doa.VehiculeRepository;
import com.parcauto.entities.Vehicule;
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
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Uni2grow
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicule")
public class VehiculeRestController {

    @Autowired
    private ModelRepository modeRep;
    @Autowired
    private VehiculeRepository vRep;
    @Autowired
    private PersonneRepository pRep;
    public static final Logger logger = LoggerFactory.getLogger(VehiculeRestController.class);

    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Vehicule>> listAll() {
        List<Vehicule> vehicules = vRep.findAll();
        if (vehicules.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Vehicule> getBrand(Long id) {
        logger.info("Fetching Vehicule with id {}", id);
        Vehicule vehicule = vRep.findOne(id);
        if (vehicule == null) {
            logger.error("Vehicule with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Vehicule with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicule, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> createVehicule(@RequestBody Vehicule vehicule, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Vehicule : {}", vehicule);
        vehicule.setModel(modeRep.findOne(vehicule.getMod_id()));
        vehicule.setPersonne(pRep.findOne(vehicule.getUser_id()));
        vRep.save(vehicule);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/vehicule/get?id={id}").buildAndExpand(vehicule.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> updateVehicule(@RequestBody Vehicule currentVehicule) {
        if (currentVehicule == null) {
            logger.error("Unable to update. Vehicule with id {} not found.", currentVehicule.getId());
            return new ResponseEntity(new CustomErrorType("Unable to upate. Vehicule with id " + currentVehicule.getId() + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        Vehicule v = vRep.findOne(currentVehicule.getId());
        if(currentVehicule.getImmatriculation()!=null) v.setImmatriculation(currentVehicule.getImmatriculation());
        if(currentVehicule.getCheveau()!=null) v.setCheveau(currentVehicule.getCheveau());
        if(currentVehicule.getPv()!=null) v.setPv(currentVehicule.getPv());
        if(currentVehicule.getPtc()!=null) v.setPtc(currentVehicule.getPtc());
        if(currentVehicule.getCouleur()!=null) v.setCouleur(currentVehicule.getCouleur());
        if(currentVehicule.getMod_id()!=null) v.setModel(modeRep.findOne(currentVehicule.getMod_id()));
        vRep.save(v);
        return new ResponseEntity<>(v, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Vehicule> deleteVehicule(@RequestBody Vehicule vehicule) {
        Long id = vehicule.getId();
        Vehicule m = vRep.findOne(id);
        logger.info("Fetching & Deleting vehicule with id {}", id);
        if (m == null) {
            logger.error("Unable to delete. vehicule with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. vehicule with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        vRep.delete(m);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
