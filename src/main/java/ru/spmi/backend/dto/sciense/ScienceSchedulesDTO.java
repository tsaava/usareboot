package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceSchedulesDTO {
    //основная информация при открытии окна
    private String science_dissertation_schedule_id;
    private String science_dissertation_id;
    private String science_schedule_type_id;
    private String schedule_type_code;
    private String schedule_type_name;
    private String date_plan;
    private String date_fact;
}
