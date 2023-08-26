package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Product;
import tn.ecnam.resources.entity.Reclamation;

import java.util.List;

public interface IReclamationService {
    Reclamation AddReclmation(Reclamation reclamation);

    void AddReclamationToUser(Reclamation reclamation) throws Exception;

    void AddReclamationToLivreur(Reclamation reclamation) throws Exception;

    Reclamation UpdateReclamtion(Reclamation reclamation);
    void deleteReclmation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    Reclamation getReclamation(String ReclamationId);
}
