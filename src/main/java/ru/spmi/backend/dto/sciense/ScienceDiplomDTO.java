package ru.spmi.backend.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceDiplomDTO {
    //основная информация при открытии окна
    private long science_dissertation_id;
    private long person_id;
    private String fio;
    private String diss_qualification_name;
    private String blank_seria;
    private String blank_num;
    private String reg_num;
    private String dis_order_num;
    private String dis_order_date;
    private String dissovet;
    private String dip_order;
    private String dis_sovet_name;
    private String date_patent;
    private String date_protection;
}
