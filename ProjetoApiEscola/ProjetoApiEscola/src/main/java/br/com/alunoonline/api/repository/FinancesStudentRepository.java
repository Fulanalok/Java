package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.FinancesStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FinancesStudentRepository extends JpaRepository<FinancesStudent, Long> {
}
