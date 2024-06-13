package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.UpdateGradesRequest;
import br.com.alunoonline.api.model.RegistrationStudent;
import br.com.alunoonline.api.service.RegistrationStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula-aluno")
public class DisciplineStudentController {

    @Autowired
    RegistrationStudentService registrationStudentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RegistrationStudent registrationStudent) {
        registrationStudentService.create(registrationStudent);
    }

    @PatchMapping("/update-grades/{RegistrationStudentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGrades(@RequestBody UpdateGradesRequest updateGradesRequest,
                             @PathVariable Long RegistrationStudentId) {
        registrationStudentService.updateGrades(RegistrationStudentId, updateGradesRequest);
    }

    @PatchMapping("/update-status-to-break/{RegistrationStudentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusToBreak(@PathVariable Long RegistrationStudentId) {
        registrationStudentService.updateStatusToBreak(RegistrationStudentId);
    }

}
