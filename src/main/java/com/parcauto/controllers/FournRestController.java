/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import static com.parcauto.controllers.MarquesRestController.logger;
import com.parcauto.doa.DevisRepository;
import com.parcauto.doa.PanneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.parcauto.doa.FournisseurRepository;
import com.parcauto.doa.MatDevisRepository;
import com.parcauto.doa.MaterielRepository;
import com.parcauto.doa.PanDeviRepository;
import com.parcauto.doa.UtilisateurRepository;
import com.parcauto.entities.Devis;
import com.parcauto.entities.Fournisseur;
import com.parcauto.entities.Marque;
import com.parcauto.entities.MatDevis;
import com.parcauto.entities.Materiel;
import com.parcauto.entities.NewDevis;
import com.parcauto.entities.Panne;
import com.parcauto.entities.PanneDevis;
import com.parcauto.entities.Utilisateur;
import com.parcauto.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping(value = "/fourn")
public class FournRestController {

    @Autowired
    private FournisseurRepository fournRp;
    @Autowired
    private PanneRepository pRep;
    @Autowired
    DevisRepository dRep;
    @Autowired
    private PanDeviRepository pdevRep;
    @Autowired
    private MaterielRepository matRep;
    @Autowired
    private MatDevisRepository mdRep;
    @Autowired
    private UtilisateurRepository userRp;

    public static final Logger logger = LoggerFactory.getLogger(FournRestController.class);

    @RequestMapping(value = "/liste", method = GET)
    public ResponseEntity<List<Fournisseur>> listAll() {
        List<Fournisseur> fourns = fournRp.findAll();
        if (fourns.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fourns, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = GET)
    public ResponseEntity<Fournisseur> getOne(Integer cni) {
        logger.info("Fetching fourn with cni {}", cni);
        Fournisseur fourn = fournRp.findOne(cni);
        if (fourn == null) {
            logger.error("Fourn with cni{} not found.", cni);
            return new ResponseEntity(new CustomErrorType("Fourn with cni " + cni
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fourn, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<String> create(@RequestBody Fournisseur fourn, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Fourn : {}", fourn);
        fourn.setId(fourn.getCni());
        fournRp.save(fourn);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/fourn/get?cni={cni}").buildAndExpand(fourn.getCni()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/create-devis", method = POST)
    public ResponseEntity<String> createDevis(@RequestBody NewDevis dev) {
        Fournisseur f = fournRp.findOne(dev.getDevis().getFourn_id());
        dev.getDevis().setFournisseur(f);
        for (Devis d : dRep.findAll()) {
            if (Objects.equals(d.getFournisseur().getId(), f.getId())) {
                return new ResponseEntity(new CustomErrorType("Le fournisseur." + f.getPrenom() + " a déja fais un dévis pour l'une des pannes choisis"),
                        HttpStatus.FORBIDDEN);
            }
        }
        dRep.save(dev.getDevis());
        for (Materiel mat : dev.getMateriels()) {
            MatDevis matD = new MatDevis(mat, dev.getDevis(), mat.getQte());
            if(!mat.getExisted()){
                matRep.save(mat);
            }
            mdRep.save(matD);
        }
        for (int i = 0; i < dev.getDevis().getPan_idd().length; i++) {
            Panne p = pRep.findOne(dev.getDevis().getPan_idd()[i]);
            pdevRep.save(new PanneDevis(p, dev.getDevis()));
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<?> update(@RequestBody Fournisseur currentFourn) {
        if (currentFourn == null) {
            logger.error("Unable to update. Fourn not found.");
            return new ResponseEntity(new CustomErrorType("Unable to upate. Fourn  not found."),
                    HttpStatus.NOT_FOUND);
        }
        Fournisseur u = fournRp.findOne(currentFourn.getId());
        if (currentFourn.getNom() != null) {
            u.setNom(currentFourn.getNom());
        }
        if (currentFourn.getPrenom() != null) {
            u.setPrenom(currentFourn.getPrenom());
        }
        if (currentFourn.getCni() != null) {
            u.setCni(currentFourn.getCni());
        }
        fournRp.save(u);
        return new ResponseEntity<>(currentFourn, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = POST)
    public ResponseEntity<Fournisseur> delete(@RequestBody Fournisseur fourn) {
        Integer cni = fourn.getId();
        System.out.println(cni);
        logger.info("Fetching & Deleting fourn with id {}", cni);
        if (fourn == null) {
            logger.error("Unable to delete. fourn with id {} not found.", cni);
            return new ResponseEntity(new CustomErrorType("Unable to delete. fourn with id " + cni + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        fournRp.delete(cni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Test Deletable and delete-----------------------------------------
    @RequestMapping(value = "/test-del-devis", method = GET)
    public ResponseEntity<Marque> deleteTest(Long id) {
        Devis dev = dRep.findOne(id);
        if (dev == null) {
            return new ResponseEntity(new CustomErrorType("impossible, devis avec l'id " + id + " Introuvable."),
                    HttpStatus.NOT_FOUND);
        }
        if (!dev.getPanneDevis().isEmpty()) {
            dev.getPanneDevis().forEach((pdev) -> {
                pdevRep.delete(pdev);
            });
        }
        dRep.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/liste-materiels", method = GET)
    public ResponseEntity<List<Materiel>> listAllMats() {
        List<Materiel> mats = matRep.findAll();
        if (mats.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mats, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-materiel", method = GET)
    public ResponseEntity<Materiel> getOneMat(Long id) {
        Materiel mat = matRep.findOne(id);
        if (mat == null) {
            return new ResponseEntity(new CustomErrorType("materiel with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mat, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getLogUser", method = GET)
    public ResponseEntity<Utilisateur> getLoged(@RequestHeader(SecurityConstants.HEADER_STRING) String token) {
        String username = null;
        if (token != null) {
            username = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET.getBytes())
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
        }
        Utilisateur user = userRp.findByUsername(username);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
