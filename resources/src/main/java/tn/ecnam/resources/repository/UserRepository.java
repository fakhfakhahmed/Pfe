package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<User,String> {

    Optional<User> findByMail(String email) ;
    Optional<User> findByUserName(String username) ;

}
