package ru.spmi.backend.dto.dictionaries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DDegreeDetailsDTO {

    private long degreeDetailId;
    private String degreeDetailCode;
    private String degreeDetailShort;
    private String degreeDetailName;
    private String science;
    private String scienceShort;
    private String scienceEng;
    private Short active;


}
