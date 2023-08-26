package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Product;
import tn.ecnam.resources.entity.Reclamation;
import tn.ecnam.resources.service.IProductService;
import tn.ecnam.resources.service.IReclamationService;

import java.util.List;

@RestController
@RequestMapping("/Reclamation")
public class ReclamationController {
    @Autowired
    IReclamationService rs;

    @PostMapping("/add-reclamation/user")
    public Reclamation addReclamationUser(@RequestBody Reclamation reclamation) throws Exception {
        Reclamation r = rs.AddReclmation(reclamation);
        rs.AddReclamationToUser(r);
        return r;
    }
    @PostMapping("/add-reclamation/Livreur")
    public Reclamation addReclamationLiv(@RequestBody Reclamation reclamation) throws Exception {
        Reclamation r = rs.AddReclmation(reclamation);
        rs.AddReclamationToLivreur(r);
        return r;
    }
    @PostMapping("/update-reclamation")
    public Reclamation UpdateReclamation(@RequestParam("ReclamationId") String reclamationId, @RequestBody Reclamation reclamation){
        reclamation.setId(reclamationId);
        return rs.UpdateReclamtion(reclamation);
    }
    @DeleteMapping("/delete-reclamation/{reclamationId}")
    public void deleteReclamation(@PathVariable("reclamationId") String reclamationId) {
        Reclamation reclamation= rs.getReclamation(reclamationId);
        rs.deleteReclmation(reclamation);
    }
    @GetMapping("/get-all")
    public List<Reclamation> getAll(){
        return rs.getAllReclamations();
    }
    @GetMapping("/{ReclamationId}")
    public Reclamation getReclamation(@PathVariable("reclamationId") String reclamationId){
        return  rs.getReclamation(reclamationId);
    }
}
