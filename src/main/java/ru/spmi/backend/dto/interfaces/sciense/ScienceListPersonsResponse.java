package ru.spmi.backend.dto.interfaces.sciense;


public interface ScienceListPersonsResponse {
    Long getperson_id();
    String getfname();
    String getiname();
    String getoname();
    String getsex();
    String getbirth_date();
    String getcountry();
    String getperson_status();
}
