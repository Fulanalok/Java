package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Fatura;
import br.com.alunoonline.api.model.FinancesStudent;
import br.com.alunoonline.api.repository.FaturaRepository;
import br.com.alunoonline.api.repository.FinancesStudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class FinancesService {
    private static final Integer QUANTITY_OF_DAYS_BEFORE_GENERATE = 10;

    private static final Logger logger = LoggerFactory.getLogger(FinancesService.class);

    @Autowired
    FinancesStudentRepository financesStudentRepository;

    @Autowired
    FaturaRepository faturaRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void faturaGeneration() {
        logger.info("Iniciando a geração de faturas...");

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime generationDeadline = currentDate.plusDays(QUANTITY_OF_DAYS_BEFORE_GENERATE);

        List<FinancesStudent> financesStudents = financesStudentRepository.findAll();

        for (FinancesStudent financesStudent : financesStudents) {
            Integer dueDay = financesStudent.getDueDate();

            if (dueDay != null) {

                LocalDate dueDateCurrentMonth = calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue());

                if (dueDateCurrentMonth.isBefore(currentDate.toLocalDate())) {
                    dueDateCurrentMonth = calculateDueDate(dueDay, currentDate.getYear(), currentDate.getMonthValue() + 1);
                }

                if (dueDateCurrentMonth != null && (dueDateCurrentMonth.isBefore(generationDeadline.toLocalDate()) || dueDateCurrentMonth.isEqual(generationDeadline.toLocalDate()))) {
                    if (faturaRepository.existsByFinancesStudentAndDueDate(financesStudent, dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT))) {
                        continue;
                    }

                    logger.info("Gerando fatura para o aluno: {}", financesStudent.getId());

                    Fatura fatura = new Fatura();
                    fatura.setStudentFinancial(financesStudent);
                    fatura.setDueDate(dueDateCurrentMonth.atTime(LocalTime.MIDNIGHT));
                    fatura.setCreatedAt(currentDate);


                    faturaRepository.save(fatura);

                    logger.info("Fatura gerada para o aluno: {} com data de vencimento: {}", financesStudent.getId(), dueDateCurrentMonth);

                }
            }

            logger.info("Geração de faturas concluída.");
        }

    }

    private LocalDate calculateDueDate(Integer dueDay, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

        int dayOfMonth = Math.min(dueDay, yearMonth.lengthOfMonth());

        return LocalDate.of(year, month, dayOfMonth);
    }
}