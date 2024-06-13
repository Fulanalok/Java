package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Subject;
import br.com.alunoonline.api.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplina")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Subject subject) {
        subjectService.create(subject);
    }

    @GetMapping("/professor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> findByProfessorId(@PathVariable Long id) {
       return subjectService.findByProfessorId(id);
    }
}
