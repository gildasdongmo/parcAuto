/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import com.parcauto.doa.DevisRepository;
import com.parcauto.doa.PanDeviRepository;
import com.parcauto.doa.TypePanRepository;
import com.parcauto.doa.PanneRepository;
import com.parcauto.doa.VehiculeRepository;
import com.parcauto.entities.Devis;
import com.parcauto.entities.TypePanne;
import com.parcauto.entities.Panne;
import com.parcauto.entities.PanneDevis;
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
@RequestMapping("/panne")
public class PannelRestController {

    @Autowired
    private PanneRepository panRep;
    @Autowired
    private TypePanRepository tp;
    @Autowired
    private VehiculeRepository vr;
    @Autowired
    private DevisRepository dRep;
    @Autowired
    private PanDeviRepository pdevRep;

    public static final Logger logger = LoggerFactory.getLogger(PannelRestController.class);

    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Panne>> listAllpanne() {
        List<Panne> pannes = panRep.findAll();
        if (pannes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pannes, HttpStatus.OK);
    }

    @RequestMapping(value = "/devis-list", method = GET)
    public ResponseEntity<List<Devis>> listAllDevis() {
        List<Devis> devis = dRep.findAll();
        if (devis.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(devis, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Panne> getBrand(Long id) {
        logger.info("Fetching Panne with id {}", id);
        Panne panne = panRep.findOne(id);
        if (panne == null) {
            logger.error("Panne with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Panne with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(panne, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> createPanne(@RequestBody Panne panne, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Panne : {}", panne);
        Panne m = new Panne(panne.getDescription(), vr.findOne(panne.getCar_id()), tp.findOne(panne.getTpan_id()));
        panRep.save(m);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/panne/get?id={id}").buildAndExpand(m.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/join-devis", method = POST)
    public ResponseEntity<String> JoinDevis(@RequestBody Devis dev, Long panId) {
        Panne p = panRep.findOne(panId);
        Devis d = dRep.findOne(dev.getPan_id());
        boolean statu = true;
        for(PanneDevis pdev: pdevRep.findAll()){
            if(pdev.getPanne().getId().equals(p.getId()) && pdev.getDevis().getId().equals(d.getId())){
                statu = false;
            }
        }
        if(statu==true){
            pdevRep.save(new PanneDevis(p, d));
        }else{
            return new ResponseEntity(new CustomErrorType("Impossible!, le dévis est dejas lié a cette panne"),
                    HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> updatePanne(@RequestBody Panne currentPanne) {
        if (currentPanne == null) {
            logger.error("Unable to update. Panne not found.");
            return new ResponseEntity(new CustomErrorType("Unable to upate. Panne not found."),
                    HttpStatus.NOT_FOUND);
        }
        Panne p = panRep.findOne(currentPanne.getId());
        p.setDescription(currentPanne.getDescription());
        panRep.save(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    // ------------------- Test Deletable-----------------------------------------
    @RequestMapping(value = "/test-deletable", method = GET)
    public ResponseEntity<Panne> deleteTest(Long id) {
        Panne panne = panRep.findOne(id);
        logger.info("Fetching & Deleting Panne");
        if (panne == null) {
            logger.error("Unable to delete. Panne not found.");
            return new ResponseEntity(new CustomErrorType("impossible, Panne avec l'id " + id + " Introuvable."),
                    HttpStatus.NOT_FOUND);
        }
        if (panne.getPanneDevis().isEmpty()) {
            panRep.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(new CustomErrorType("Impossible de suprimer"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Panne> deletePanne(@RequestBody Panne panne) {
        Long id = panne.getId();
        Panne m = panRep.findOne(id);
        logger.info("Fetching & Deleting panne with id {}", id);
        if (m == null) {
            logger.error("Unable to delete. panne with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. panne with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        panRep.delete(m);
        return new ResponseEntity<Panne>(HttpStatus.NO_CONTENT);
    }

}
