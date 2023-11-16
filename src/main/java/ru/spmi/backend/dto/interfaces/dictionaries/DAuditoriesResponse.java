package ru.spmi.backend.dto.interfaces.dictionaries;

public interface DAuditoriesResponse {
    long getauditoryId();
    long getducationCenterId();
    long getbuildingId();
    long getauditoryTypeId();
    String getauditoryCode();
    String getauditoryName();
    int getactive();
}
