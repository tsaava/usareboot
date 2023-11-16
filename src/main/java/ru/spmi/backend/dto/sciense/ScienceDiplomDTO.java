package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceDiplomDTO {
    //основная информация при открытии окна
    private long scienceDissertationId;
    private long personId;
    private String fio;
    private String dissQualificationName;
    private String blankSeria;
    private String blankNum;
    private String regNum;
    private String disOrderNum;
    private String disOrderDate;
    private String dissovet;
    private String dipOrder;
    private String disSovetName;
    private String datePatent;
    private String dateProtection;
}
