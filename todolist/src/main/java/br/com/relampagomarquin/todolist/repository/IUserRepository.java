package br.com.relampagomarquin.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.relampagomarquin.todolist.models.UserModel;


public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    
    UserModel findByUsername(String username);

}
