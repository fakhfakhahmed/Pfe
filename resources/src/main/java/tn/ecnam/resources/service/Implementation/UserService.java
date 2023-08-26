package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository ur;
    @Override
    public User AddUser(User user) {
      return ur.save(user);
    }

    @Override
    public User LoggedInUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findUserWithUsername(username);
    }

    @Override
    public User findUserWithEmail(String email) throws Exception {
        if(ur.findByMail(email).isEmpty()){
            throw  new Exception( "there is no  user with  email : " +email) ;
        }
        return  ur.findByMail(email).get() ;
    }
    @Override
    public User findUserWithUsername(String Username) throws Exception {
        Optional<User> optionalUser = ur.findByUserName(Username);
        if(optionalUser.isPresent()){
            return  optionalUser.get() ;
        }
        throw  new Exception( "there is no  user with  email : " +Username) ;
    }

    @Override
    public User UpdateUser(User user) {
      return   ur.save(user);
    }

    @Override
    public void deleteUser(User user) {
            ur.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>)  ur.findAll();
    }

    @Override
    public User getUserById(String UserId) {
        return ur.findById(UserId).orElse(null);
    }
}
