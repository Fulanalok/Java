package br.com.alunoonline.api.controller;


import br.com.alunoonline.api.model.Trainee;
import br.com.alunoonline.api.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Trainee")
@RestController
public class TraineeController {

    @Autowired
    TraineeService traineeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Trainee trainee ) { traineeService.create(trainee); }
}
