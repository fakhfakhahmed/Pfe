package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.Reclamation;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.LivreurRepository;
import tn.ecnam.resources.repository.ReclamationRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.ILivreurService;
import tn.ecnam.resources.service.IReclamationService;
import tn.ecnam.resources.service.IUserService;

import java.util.List;
import java.util.Set;


@Service
public class ReclamationService implements IReclamationService {
    @Autowired
    ReclamationRepository rr;
    @Autowired
    private IUserService us;
    @Autowired
    private ILivreurService ls;
    @Autowired
    private LivreurRepository lr;
    @Autowired
    private UserRepository ur;
    @Override
    public Reclamation AddReclmation(Reclamation reclamation) {
        return  rr.save(reclamation);
    }

    @Override
    public void AddReclamationToUser(Reclamation reclamation) throws Exception {
        User user = us.LoggedInUser();
        Set<Reclamation> reclamations = user.getReclamations();
        reclamations.add(reclamation);
        user.setReclamations(reclamations);
        ur.save(user);
    }
    @Override
    public void AddReclamationToLivreur(Reclamation reclamation) throws Exception {
        Livreur livreur = ls.LoggedInLivreur();
        Set<Reclamation> reclamations = livreur.getReclamations();
        reclamations.add(reclamation);
        livreur.setReclamations(reclamations);
        lr.save(livreur);
    }

    @Override
    public Reclamation UpdateReclamtion(Reclamation reclamation) {
        return rr.save(reclamation);
    }

    @Override
    public void deleteReclmation(Reclamation reclamation) {
        rr.delete(reclamation);

    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return (List<Reclamation>)  rr.findAll();
    }

    @Override
    public Reclamation getReclamation(String ReclamationId) {
         return rr.findById(ReclamationId).orElse(null);
    }
}
