package com.usareboot.back.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceListPersonsDTO {
    //для вывода списка персон
    private long personId;
    private String fName;
    private String iName;
    private String oName;
    private String sex;
    private String birthDate;
    private String country;
    private String personStatus;
}
