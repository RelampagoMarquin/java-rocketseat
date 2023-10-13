package br.com.relampagomarquin.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class todoCotroller {
    
    @GetMapping("/")
    public String getAll(){
        return "No futuro retornarei até a mãe";
    }
}
