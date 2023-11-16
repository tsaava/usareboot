package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScienceDateRequestDTO {
    private Date dateDog;
    private Date dateDefense;
    private String timeDefense;
    private String auditory;
}
