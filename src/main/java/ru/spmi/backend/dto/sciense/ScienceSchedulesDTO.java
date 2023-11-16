package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceSchedulesDTO {
    //основная информация при открытии окна
    private String scienceDissertationScheduleId;
    private String scienceDissertationId;
    private String scienceScheduleTypeId;
    private String scheduleTypeCode;
    private String scheduleTypeName;
    private String datePlan;
    private String dateFact;
}
