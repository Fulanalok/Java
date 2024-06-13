package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TraineeRepository extends JpaRepository<Trainee , Long> {

}
