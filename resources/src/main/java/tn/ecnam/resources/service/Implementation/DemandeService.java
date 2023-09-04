package tn.ecnam.resources.service.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.config.QueueSender;
import tn.ecnam.resources.entity.DTO.UserLivreurDemandeDTO;
import tn.ecnam.resources.entity.Demande;
import tn.ecnam.resources.entity.Livreur;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.DemandeRepository;
import tn.ecnam.resources.repository.LivreurRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IDemandeService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class DemandeService implements IDemandeService {

    @Autowired
    DemandeRepository dr;
    @Autowired
    UserService us;
    @Autowired
    UserRepository ur;
    @Autowired
    LivreurRepository lr;
    @Autowired
    LivreurService ls;
    @Autowired
    private QueueSender queueSender;
    @Override
    public Demande AddDemande(Demande demande) {
        return dr.save(demande);
    }

    @Override
    @Transactional
    public Demande AddDemandeToUser(Demande demande) throws Exception {
        User user = us.LoggedInUser();
        demande.setEtatDemande("Pending");
        demande.setDate(new Date());
        demande = dr.save(demande);
        Set<Demande> demandes = user.getDemandes();
        demandes.add(demande);
        user.setDemandes(demandes);
        ur.save(user);
        return demande;
    }

    @Override
    public void AccptedDemande(String demandeId)throws Exception {
        Livreur livreur =ls.LoggedInLivreur();
        Demande demande = dr.findById(demandeId).get();
        demande.setEtatDemande("Accepted");
        demande=dr.save(demande);


        Set<Demande> demandes = livreur.getDemandes();
        demandes.add(demande);
        livreur.setDemandes(demandes);
        lr.save(livreur);

        User user = ur.findUserByDemandeId(demandeId);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Update etatDemande in User's demandes
        for (Demande userDemande : user.getDemandes()) {
            if (userDemande.getId().equals(demandeId)) {
                userDemande.setEtatDemande("Accepted");
            }
        }
        ur.save(user);

        // Create the DTO object
        UserLivreurDemandeDTO dto = new UserLivreurDemandeDTO();
        dto.setUserName(user.getUserName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLivreurName(livreur.getUserName());
        dto.setDemandeId(demandeId);

        // Convert the DTO to JSON and send it
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(dto);
        log.info(jsonMessage);
        queueSender.send(jsonMessage);

    }

    @Override
    @Transactional
    public Demande UpdateDemandeForLoggedInUser(Demande updatedDemande) throws Exception {
        User user = us.LoggedInUser();
        Demande existingDemande = dr.findById(updatedDemande.getId()).orElseThrow(() -> new Exception("Demande not found"));

        if (!user.getDemandes().contains(existingDemande)) {
            throw new Exception("You are not authorized to update this demande.");
        }
        existingDemande.setEtatDemande(updatedDemande.getEtatDemande());
        existingDemande.setDate(updatedDemande.getDate());
        existingDemande.setLocation(updatedDemande.getLocation());
        return dr.save(existingDemande);
    }

    @Override
    public void deleteDemandeById(String demandeId) throws Exception {
        User user = us.LoggedInUser();
        Demande demande = dr.findById(demandeId).orElseThrow(() -> new Exception("Demande not found"));

        if (!user.getDemandes().contains(demande)) {
            throw new Exception("You are not authorized to delete this demande.");
        }
        else {
            Set<Demande> userDemandes = user.getDemandes();
            userDemandes.remove(demande);
            user.setDemandes(userDemandes);
            ur.save(user);
        }

        dr.delete(demande);
    }


    @Override
    public Demande UpdateDemande(Demande demande) {
       return dr.save(demande);
    }

    @Override
    public void deleteDemande(Demande demande) {
        dr.delete(demande);
    }

    @Override
    public List<Demande> getAllDemande() {
        return (List<Demande>)  dr.findAll();
    }

    @Override
    public Demande getDemande(String DemandeId) {
        return dr.findById(DemandeId).orElse(null);
    }
}
