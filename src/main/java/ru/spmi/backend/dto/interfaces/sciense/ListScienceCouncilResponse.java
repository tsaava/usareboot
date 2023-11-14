package ru.spmi.backend.dto.interfaces.sciense;


public interface ListScienceCouncilResponse {


    Long getscience_council_spec_id();
    Long getscience_council_id();
    Long getspec_id();
    Long getorder_id();
    String getorder_number();
    String getorder_date();
    String getscience_council_name();
    String getdate_from();
    String getdate_to();
    String getspec_name();
    String getphone();
    String gete_mail();

}
