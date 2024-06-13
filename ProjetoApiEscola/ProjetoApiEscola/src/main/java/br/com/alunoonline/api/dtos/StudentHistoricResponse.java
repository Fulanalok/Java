package br.com.alunoonline.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StudentHistoricResponse {
    private String studentName;
    private String studentEmail;
    private List<DisciplinesStudentResponse> studentSubjectsResponseList;
}