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
    private Long buildingId;
    private Long auditoryTypeId;
    private String auditoryCode;
    private String auditoryName;
    private int sd;
    private int active;
}
