/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parcauto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Uni2grow
 */
@Controller
public class indexController {
    
    @RequestMapping("/index")
    public String page(Model model) {
        model.addAttribute("name", "gildas");
        return "index";
    }
    
}
