package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.DisciplinesStudentResponse;
import br.com.alunoonline.api.dtos.UpdateGradesRequest;
import br.com.alunoonline.api.dtos.StudentHistoricResponse;
import br.com.alunoonline.api.enums.RegistrationStudentStatusEnum;
import br.com.alunoonline.api.model.RegistrationStudent;
import br.com.alunoonline.api.repository.RegistrationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class RegistrationStudentService {

    public static final double GRADE_AVG_TO_APPROVE = 7.0;

    @Autowired
    RegistrationStudentRepository registrationStudentRepository;
    private br.com.alunoonline.api.model.RegistrationStudent[] RegistrationStudent;

    public void create(RegistrationStudent registrationStudent) {
        registrationStudent.setStatus(RegistrationStudentStatusEnum.MATRICULADO);
        registrationStudentRepository.save(registrationStudent);
    }

    public void updateGrades(Long registrationStudentId, UpdateGradesRequest updateGradesRequest) {
        RegistrationStudent registrationStudent =
                registrationStudentRepository.findById(registrationStudentId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
        updateStudentGrades(registrationStudent, updateGradesRequest);
        updateStudentStatus(registrationStudent);

        registrationStudentRepository.save(registrationStudent);
    }

    public void updateStudentGrades(RegistrationStudent registrationStudent, UpdateGradesRequest updateGradesRequest) {
        if (updateGradesRequest.getGrade1() != null) {
            registrationStudent.setGrade1(updateGradesRequest.getGrade1());
        }

        if (updateGradesRequest.getGrade2() != null) {
            registrationStudent.setGrade2(updateGradesRequest.getGrade2());
        }
    }

    public void updateStudentStatus(RegistrationStudent registrationStudent) {
        Double grade1 = registrationStudent.getGrade1();
        Double grade2 = registrationStudent.getGrade2();

        if (grade1 != null && grade2 != null) {
            double average = (grade1 + grade2) / 2;
            registrationStudent.setStatus(average >= GRADE_AVG_TO_APPROVE ? RegistrationStudentStatusEnum.APROVADO : RegistrationStudentStatusEnum.REPROVADO);
        }

    }

    public void updateStatusToBreak(Long registrationStudentId) {
        RegistrationStudent registrationStudent =
                registrationStudentRepository.findById(registrationStudentId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        if (!RegistrationStudentStatusEnum.MATRICULADO.equals(registrationStudent.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matrícula com o status MATRICULADO");
        }

        changeStatus(registrationStudent, RegistrationStudentStatusEnum.TRANCADO);
    }

    public void changeStatus(RegistrationStudent registrationStudent, RegistrationStudentStatusEnum registrationStudentStatusEnum) {
        registrationStudent.setStatus(registrationStudentStatusEnum);
        registrationStudentRepository.save(registrationStudent);
    }

    public StudentHistoricResponse getHistoricFromStudent(Long studentId) {
        List<RegistrationStudent> registrationStudent = registrationStudentRepository.findByStudentId(studentId);

        if (registrationStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "esse aluno nao possui matriculas");
        }

        StudentHistoricResponse historic = new StudentHistoricResponse();
        historic.setStudentName(registrationStudent.get(0).getStudent().getName());
        historic.setStudentEmail(registrationStudent.get(0).getStudent().getEmail());

        List<DisciplinesStudentResponse> disciplinesList = new ArrayList<>();

        for (RegistrationStudent registration : RegistrationStudent) {
            DisciplinesStudentResponse disciplineStudentResponse = new DisciplinesStudentResponse();
            disciplineStudentResponse.setSubjectName(registration.getSubject().getName());
            disciplineStudentResponse.setProfessorName(registration.getSubject().getProfessor().getName());
            disciplineStudentResponse.setGrade1(registration.getGrade1());
            disciplineStudentResponse.setGrade2(registration.getGrade2());
        }
    return null;}
}
