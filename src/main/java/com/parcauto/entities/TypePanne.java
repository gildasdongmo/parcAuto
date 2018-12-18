package com.parcauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TypePanne implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String libelle;
    @OneToMany(mappedBy = "typePanne", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Panne> pannes;

    public TypePanne() {
    }

    public TypePanne(String libelle) {
        this.libelle = libelle;
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

    public Set<Panne> getPannes() {
        return pannes;
    }

    public void setPannes(Set<Panne> pannes) {
        this.pannes = pannes;
    }

}
