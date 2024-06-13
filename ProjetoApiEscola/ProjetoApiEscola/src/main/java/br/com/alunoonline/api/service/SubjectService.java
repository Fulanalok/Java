package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Subject;
import br.com.alunoonline.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public void create(Subject subject) {
        subjectRepository.save(subject);
    }

    public List<Subject> findByProfessorId(Long professorId) {
        return subjectRepository.findByProfessorId(professorId);
    }
}
