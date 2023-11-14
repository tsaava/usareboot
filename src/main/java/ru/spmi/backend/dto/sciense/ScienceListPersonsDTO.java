package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceListPersonsDTO {
    //для вывода списка персон
    private long person_id;
    private String fname;
    private String iname;
    private String oname;
    private String sex;
    private String birth_date;
    private String country;
    private String person_status;
}
