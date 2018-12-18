package com.parcauto.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PanneDevis implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Panne panne;
    @ManyToOne
    private Devis devis;

    public PanneDevis() {
    }

    public PanneDevis(Panne panne, Devis devis) {
        this.panne = panne;
        this.devis = devis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panne getPanne() {
        return panne;
    }

    public void setPanne(Panne panne) {
        this.panne = panne;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }
    
   
}
