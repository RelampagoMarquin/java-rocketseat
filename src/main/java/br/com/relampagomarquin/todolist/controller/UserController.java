package br.com.relampagomarquin.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.relampagomarquin.todolist.models.UserModel;
import br.com.relampagomarquin.todolist.repository.IUserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserModel userModel){
        var user = userRepository.findByUsername(userModel.getUsername());
        if(user != null ){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario já existe");
        }
        var pass = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(pass);
        user = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
