package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.service.ILivreurService;
import tn.ecnam.resources.service.IUserService;

@RestController
@RequestMapping("/Livreur")
public class LivreurController {
    @Autowired
    ILivreurService ls;


    @PostMapping("/add-livreur")
    public Livreur addLivreur(@RequestBody Livreur livreur){
        return ls.AddLivreur(livreur);
    }
}
