package ru.spmi.backend.dto.dictionaries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DAuditoriesDTO {
    private long auditoryId;
    private long educationCenterId;
    private long buildingId;
    private long auditoryTypeId;
    private String auditoryCode;
    private String auditoryName;

}
