package com.parcauto.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MatDevis implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Materiel materiel;
    @ManyToOne
    private Devis devis;
    private Integer qte;


    public MatDevis() {
    }

    public MatDevis(Materiel materiel, Devis devis, Integer qte) {
        this.materiel = materiel;
        this.devis = devis;
        this.qte = qte;
    }
    
     public Integer getPt() {
        return this.materiel.getPu()*this.qte;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

}
