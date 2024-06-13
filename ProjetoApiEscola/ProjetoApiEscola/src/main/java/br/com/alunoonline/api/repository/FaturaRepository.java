package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Fatura;
import br.com.alunoonline.api.model.FinancesStudent;

import java.time.LocalDateTime;

public interface FaturaRepository {
    boolean existsByFinancesStudentAndDueDate(FinancesStudent financesStudent, LocalDateTime localDateTime);

    void save(Fatura fatura);
}
