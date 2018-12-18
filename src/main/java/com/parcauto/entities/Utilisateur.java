package com.parcauto.entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Utilisateur extends Personne implements Serializable {

    private String username;
    private String password;
    private String role;
    private boolean statut;
    private String changState;

    public Utilisateur() {
    }

    public Utilisateur(String username, String password, String nom, String prenom, Integer cni, String role) {
        super(nom, prenom, cni);
        this.username = username;
        this.password = password;
        this.role = role;
        statut = false;
    }

    public Utilisateur(String nom, String prenom, Integer cni) {
        super(nom, prenom, cni);
        statut = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getChangState() {
        return changState;
    }

    public void setChangState(String changState) {
        this.changState = changState;
    }

}
