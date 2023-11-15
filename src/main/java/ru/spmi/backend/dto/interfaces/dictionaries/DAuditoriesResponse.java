package ru.spmi.backend.dto.interfaces.dictionaries;

public interface DAuditoriesResponse {
    Long getauditoryId();
    Long getducationCenterId();
    Long getbuildingId();
    Long getauditoryTypeId();
    String getauditoryCode();
    String getauditoryName();
    int getactive();
}
