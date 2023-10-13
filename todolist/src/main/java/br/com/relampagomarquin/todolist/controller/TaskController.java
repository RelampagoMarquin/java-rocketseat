package br.com.relampagomarquin.todolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.relampagomarquin.todolist.models.TaskModel;
import br.com.relampagomarquin.todolist.repository.ITaskRepository;
import br.com.relampagomarquin.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity createTask(@RequestBody TaskModel taskModel, HttpServletRequest req){
        taskModel.setIdUser((UUID) req.getAttribute("idUser"));
        var task = this.taskRepository.save(taskModel);
        LocalDateTime current = LocalDateTime.now();
        if(current.isAfter(task.getStartAt()) || current.isAfter(task.getEndAt()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial/final deve ser maior que a atual");
        }
        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial deve ser menor que a final");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> getAllTasksByUser(HttpServletRequest req){
        var tasks = this.taskRepository.findByIdUser((UUID) req.getAttribute("idUser"));
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity UpdateTask(@RequestBody TaskModel taskModel, HttpServletRequest req, @PathVariable UUID id ){
        var task = this.taskRepository.findById(id).orElse(null);
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task não encontrada");
        }
        if(!task.getIdUser().equals((UUID) req.getAttribute("idUser"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não é o criador da pergunta");
        }
        Utils.copyNonNullProperties(taskModel, task);
        this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
