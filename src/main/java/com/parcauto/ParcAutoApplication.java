package com.parcauto;

import com.parcauto.doa.MarqueRepository;
import com.parcauto.doa.ModelRepository;
import com.parcauto.doa.PersonneRepository;
import com.parcauto.doa.UtilisateurRepository;
import com.parcauto.entities.Marque;
import com.parcauto.entities.Model;
import com.parcauto.entities.Personne;
import com.parcauto.entities.Utilisateur;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ParcAutoApplication implements CommandLineRunner {

    @Autowired
    private MarqueRepository mr;
    @Autowired
    private UtilisateurRepository ur;

    public static void main(String[] args) {
        SpringApplication.run(ParcAutoApplication.class, args);

    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void run(String... strings) throws Exception {
//        Collection<Personne> m = modr.findAll();
//        for (Personne mod : m) {
//            System.out.println(mod.getNom()+' '+mod.getPrenom()+' '+mod.getCni());
//        }
    Utilisateur u = new Utilisateur("admin", bCryptPasswordEncoder().encode("admin123"), "dongmo", "gildas", 123456789, "ROLE_ADMIN");
    u.setStatut(true);
    ur.save(u);
    }
}
