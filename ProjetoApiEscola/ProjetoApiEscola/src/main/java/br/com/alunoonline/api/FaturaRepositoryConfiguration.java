package br.com.alunoonline.api;

import br.com.alunoonline.api.model.Fatura;
import br.com.alunoonline.api.model.FinancesStudent;
import br.com.alunoonline.api.repository.FaturaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class FaturaRepositoryConfiguration {

    @Bean
    public FaturaRepository faturaRepository() {
        return new FaturaRepository() {
            @Override
            public boolean existsByFinancesStudentAndDueDate(FinancesStudent financesStudent, LocalDateTime localDateTime) {
                return false;
            }

            @Override
            public void save(Fatura fatura) {

            }
        };
    }
}