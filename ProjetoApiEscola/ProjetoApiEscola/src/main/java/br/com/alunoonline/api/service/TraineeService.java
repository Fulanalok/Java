package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Trainee;
import br.com.alunoonline.api.repository.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {

    @Autowired
    TraineeRepository traineeRepository;

    public void create(Trainee trainee) {traineeRepository.save(trainee);}
}
