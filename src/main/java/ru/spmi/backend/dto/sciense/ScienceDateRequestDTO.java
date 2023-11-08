package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScienceDateRequestDTO {
    private Date date_dog;
    private Date date_defense;
    private String time_defense;
    private String auditory;
}
