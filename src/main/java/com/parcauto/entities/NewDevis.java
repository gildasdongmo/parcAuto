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
public class NewDevis implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Devis devis;
    @OneToMany(mappedBy = "newdevis", fetch = FetchType.EAGER)
    private Set<Materiel> materiels;

    public NewDevis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public Set<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(Set<Materiel> materiels) {
        this.materiels = materiels;
    }

  

}
