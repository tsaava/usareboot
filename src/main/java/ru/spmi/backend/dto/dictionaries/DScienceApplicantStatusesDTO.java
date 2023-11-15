package ru.spmi.backend.dto.dictionaries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DScienceApplicantStatusesDTO {
    private long scienceApplicantStatusId;
    private String statusShort;
    private String statusName;
    private short active;


}
