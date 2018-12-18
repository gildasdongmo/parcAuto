/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.doa;

import com.parcauto.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Uni2grow
 */
public interface ModelRepository extends JpaRepository<Model, Long> {
    
}
