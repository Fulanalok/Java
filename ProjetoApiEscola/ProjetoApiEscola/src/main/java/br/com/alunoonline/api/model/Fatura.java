package br.com.alunoonline.api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Fatura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "student_finances_id")
    private FinancesStudent studentFinances;

    private LocalDateTime dueDate;

    private LocalDateTime paidOn;

    private LocalDateTime createdAt;

    public void setDueDate(LocalDateTime localDateTime) {
    }

    public void setCreatedAt(LocalDateTime currentDate) {
    }

    public void setStudentFinancial(FinancesStudent financesStudent) {
    }
}