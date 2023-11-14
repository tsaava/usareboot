package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListScienceCouncilDTO {
    //для вывода списка персон
    private long science_council_spec_id;
    private long  science_council_id;
    private long spec_id;
    private long order_id;
    private String order_number;
    private String order_date;
    private String science_council_name;
    private String date_from;
    private String date_to;
    private String spec_name;
    private String phone;
    private String e_mail;
}
