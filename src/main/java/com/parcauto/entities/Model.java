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
public class Model implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String libelle;
    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Vehicule> vehicules;
    @ManyToOne
    private Marque marque;
    private Long brand_id;

    public Model() {
    }

    public Model(String libelle, Marque marque) {
        this.libelle = libelle;
        this.marque = marque;
    }
    public String getMaLibelle(){
        return this.marque.getLibelle();
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }
    
    
}
