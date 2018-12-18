package com.parcauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Fournisseur extends Personne implements Serializable {

    @OneToMany(mappedBy = "fournisseur", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Devis> devis;

    public Fournisseur() {

    }

    public Fournisseur(String nom, String prenom, Integer cni) {
        super(nom, prenom, cni);
    }

    public Set<Devis> getDevis() {
        return devis;
    }

    public void setDevis(Set<Devis> devis) {
        this.devis = devis;
    }

    @Override
    public void setVehicule(Vehicule vehicule) {
        super.setVehicule(vehicule); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vehicule getVehicule() {
        return super.getVehicule(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCni(Integer cni) {
        super.setCni(cni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getCni() {
        return super.getCni(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPrenom(String prenom) {
        super.setPrenom(prenom); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPrenom() {
        return super.getPrenom(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNom(String nom) {
        super.setNom(nom); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNom() {
        return super.getNom(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(Integer id) {
        super.setId(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    


}
