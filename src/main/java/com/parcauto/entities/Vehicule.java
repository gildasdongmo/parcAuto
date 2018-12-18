package com.parcauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Vehicule implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String immatriculation;
    private Integer cheveau;
    private String pv;
    private String ptc;
    private String couleur;
    @ManyToOne
    private Model model;
    @OneToOne
    private Personne personne;
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Panne> pannes;
    private Long mod_id;
    private Integer user_id;

    public Vehicule() {
    }

    public Vehicule(String immatriculation, Integer cheveau, String pv, String ptc, String couleur, Model model, Personne personne) {
        this.immatriculation = immatriculation;
        this.cheveau = cheveau;
        this.pv = pv;
        this.ptc = ptc;
        this.couleur = couleur;
        this.model = model;
        this.personne = personne;
    }

    public String getMoLibelle() {
        return this.model.getMarque().getLibelle() + ' ' + this.model.getLibelle();
    }

    public String getUser_name() {
        return this.personne.getPrenom() + ' ' + this.personne.getNom();
    }

    public Long getMod_id() {
        return mod_id;
    }

    public void setMod_id(Long mod_id) {
        this.mod_id = mod_id;
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Integer getCheveau() {
        return cheveau;
    }

    public void setCheveau(Integer cheveau) {
        this.cheveau = cheveau;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getPtc() {
        return ptc;
    }

    public void setPtc(String ptc) {
        this.ptc = ptc;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Set<Panne> getPannes() {
        return pannes;
    }

    public void setPannes(Set<Panne> pannes) {
        this.pannes = pannes;
    }

}
