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

@Entity
public class Materiel implements Serializable {

    @Id
    @GeneratedValue
    private Long idd;
    private String description;
    private String libelle;
    private Integer pu;
    private String id;
    @ManyToOne
    private Marque newdevis;
    private Integer qte;
    private boolean existed;
    
    @OneToMany(mappedBy = "materiel", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<MatDevis> matDevises;

    public Materiel() {
        existed = false;
    }
    
    
    public Long getIdd() {
        return idd;
    }

    public void setIdd(Long idd) {
        this.idd = idd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPu() {
        return pu;
    }

    public void setPu(Integer pu) {
        this.pu = pu;
    }
    
    public Marque getNewdevis() {
        return newdevis;
    }

    public void setNewdevis(Marque newdevis) {
        this.newdevis = newdevis;
    }


    public Set<MatDevis> getMatDevises() {
        return matDevises;
    }

    public void setMatDevises(Set<MatDevis> matDevises) {
        this.matDevises = matDevises;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public boolean getExisted() {
        return existed;
    }

    public void setExisted(boolean existed) {
        this.existed = existed;
    }

}
