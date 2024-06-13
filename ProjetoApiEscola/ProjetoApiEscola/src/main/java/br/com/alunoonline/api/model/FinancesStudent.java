package br.com.alunoonline.api.model;

import br.com.alunoonline.api.enums.FinancesStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class FinancesStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToOne
    @JoinColumn(name = "Student_id")
    private Student student;

    private Double discount;

    private Integer dueDate;

    @Enumerated(EnumType.STRING)
    private FinancesStatusEnum status;
}
