package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.service.IUserService;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    IUserService us;


    @PostMapping("/add-user")
    public User addUser(@RequestBody User user){
        return us.AddUser(user);
    }
}
