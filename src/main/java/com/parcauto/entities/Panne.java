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
public class Panne implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @ManyToOne
    private Vehicule vehicule;
    @ManyToOne
    private TypePanne typePanne;
    @OneToMany(mappedBy = "panne", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PanneDevis> panneDevis;
    private Long tpan_id;
    private Long car_id;
    public Panne() {
    }

    public Panne(String description, Vehicule vehicule, TypePanne typePanne) {
        this.description = description;
        this.vehicule = vehicule;
        this.typePanne = typePanne;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public TypePanne getTypePanne() {
        return typePanne;
    }

    public void setTypePanne(TypePanne typePanne) {
        this.typePanne = typePanne;
    }

    public Set<PanneDevis> getPanneDevis() {
        return panneDevis;
    }

    public void setPanneDevis(Set<PanneDevis> panneDevis) {
        this.panneDevis = panneDevis;
    }

    public Long getTpan_id() {
        return tpan_id;
    }

    public void setTpan_id(Long tpan_id) {
        this.tpan_id = tpan_id;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }
    
    public String getTpLibelle(){
        return this.typePanne.getLibelle();
    }
    
    public String getCarLibelle(){
        return this.vehicule.getImmatriculation()+' '+this.vehicule.getModel().getMarque().getLibelle()+' '+this.vehicule.getModel().getLibelle();
    }

}
