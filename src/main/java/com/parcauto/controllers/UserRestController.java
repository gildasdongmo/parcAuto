/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import com.parcauto.doa.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.parcauto.doa.UtilisateurRepository;
import com.parcauto.entities.Personne;
import com.parcauto.entities.Utilisateur;
import com.parcauto.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping(value = "/user")
public class UserRestController {

    @Autowired
    private UtilisateurRepository userRp;
    @Autowired
    private PersonneRepository pRep;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Utilisateur>> listAll() {
        List<Utilisateur> users = userRp.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/personnes/liste", method = GET)
    public ResponseEntity<List<Personne>> listAllPersonnes() {
        List<Personne> personnes = pRep.findAll();
        if (personnes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(personnes, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Utilisateur> getOne(Integer cni) {
        logger.info("Fetching User with cni {}", cni);
        Utilisateur user = userRp.findOne(cni);
        if (user == null) {
            logger.error("User with cni{} not found.", cni);
            return new ResponseEntity(new CustomErrorType("User with cni " + cni
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> create(@RequestBody Utilisateur user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
        user.setId(user.getCni());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setChangState("Desactiver");
        userRp.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/get?cni={cni}").buildAndExpand(user.getCni()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> update(@RequestBody Utilisateur currentUser) {
        if (currentUser == null) {
            logger.error("Unable to update. User not found.");
            return new ResponseEntity(new CustomErrorType("Unable to upate. User  not found."),
                    HttpStatus.NOT_FOUND);
        }
        Utilisateur u = userRp.findOne(currentUser.getId());
        if (currentUser.getNom() != null) {
            u.setNom(currentUser.getNom());
        }
        if (currentUser.getPrenom() != null) {
            u.setPrenom(currentUser.getPrenom());
        }
        if (currentUser.getCni() != null) {
            u.setCni(currentUser.getCni());
        }
        if (currentUser.getUsername() != null) {
            u.setUsername(currentUser.getUsername());
        }
        if (currentUser.getRole() != null) {
            u.setRole(currentUser.getRole());
        }
        if (currentUser.getChangState() != null) {
            if ("Activer".equals(currentUser.getChangState())) {
                u.setStatut(true);
                u.setChangState("Activer");
            } else if ("Desactiver".equals(currentUser.getChangState())) {
                u.setStatut(false);
                u.setChangState("Desactiver");
            }
        }
        userRp.save(u);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Utilisateur> delete(@RequestBody Utilisateur user) {
        Integer cni = user.getId();
        System.out.println(cni);
        logger.info("Fetching & Deleting User with id {}", cni);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", cni);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + cni + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userRp.delete(cni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
