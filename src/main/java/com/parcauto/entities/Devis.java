package com.parcauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Devis implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String date;
    @ManyToOne
    private Fournisseur fournisseur;
    @OneToMany(mappedBy = "devis", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PanneDevis> panneDevis;
    private Long[] pan_idd;
    private Long pan_id;
    private Integer fourn_id;
    @OneToMany(mappedBy = "devis", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<MatDevis> matDevises;
    private String delai;
    private Integer frai_rep;
    public Devis() {

    }

    public Long getId() {
        return id;
    }
    
    public String getMontant(){
        Integer amount = 0;
        for(MatDevis mat: this.matDevises){
            amount = amount + mat.getPt();
        }
        if(this.frai_rep!=null){
            amount = amount + this.frai_rep;
        }
        return amount+ " F.CFA";
    }
    
    public String getFourn_name(){
        return this.fournisseur.getPrenom()+' '+this.fournisseur.getNom();
    }
    
    public Integer getNbr_mat(){
        return this.matDevises.size();
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Set<PanneDevis> getPanneDevis() {
        return panneDevis;
    }

    public void setPanneDevis(Set<PanneDevis> panneDevis) {
        this.panneDevis = panneDevis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long[] getPan_idd() {
        return pan_idd;
    }

    public void setPan_idd(Long[] pan_idd) {
        this.pan_idd = pan_idd;
    }

    public Long getPan_id() {
        return pan_id;
    }

    public void setPan_id(Long pan_id) {
        this.pan_id = pan_id;
    }

    public Set<MatDevis> getMatDevises() {
        return matDevises;
    }

    public void setMatDevises(Set<MatDevis> matDevises) {
        this.matDevises = matDevises;
    }

    public Integer getFourn_id() {
        return fourn_id;
    }

    public void setFourn_id(Integer fourn_id) {
        this.fourn_id = fourn_id;
    }

    public String getDelai() {
        return delai;
    }

    public void setDelai(String delai) {
        this.delai = delai;
    }

    public Integer getFrai_rep() {
        return frai_rep;
    }

    public void setFrai_rep(Integer frai_rep) {
        this.frai_rep = frai_rep;
    }
    
    
}
