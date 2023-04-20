package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceDTO {
    //основная информация при открытии окна
    private int count_rows;
    private String fio_full;
    private String okonchanie_instituta;
    private String dis_sovet_name;
    private String diss_qualification_name;
    private String date_defense;
    private String time_defense;
    private String auditory;

    private String date_admission;
    private String date_vak;
    private String date_minus_10;
    private String date_minus_5;
    private String date_plus_10;
    private String date_plus_15;
    private String date_plus_30;

    private String fio_scientific_adviser;
    private String okso_ds;
    private String diss_spec_name_ds;
    private String theme;
    private String diss_status;

    //    информация при просмотре детализации
    private String date_doc_details;
    private String protocol_1_details;
    private String date_protocol_1_details;
    private String protocol_2_details;
    private String date_protocol_2_details;
    private String protocol_3_details;
    private String date_protocol_3_details;
    private String certification_case_details;
    private String certification_date_details;
    private String protocol_4_details;
    private String date_protocol_4_details;

    private String order_number_details;
    private String order_date_details;
    private String diplom_num_details;
    private String diplom_seria_details;
    private String url_vak_info;
    private String comment_details;

    //    информация о соискателе (общая)
    private String dolshnost_info;
    private String birth_date_info;
    private String phone_info;
    private String e_mail_info;
    private String age_info;
    private String sex_info;
    private String country_info;
    private String emp_type_info;

    private String qr_code_info;
    private String protocol_3_count_presents_info;
    private String protocol_3_count_voite_info;
    private String protocol_3_count_voite_not_info;

    private String protocol_3_count_voite_forgo_info;
    private String protocol_3_count_voite_bad_info;



   /* private String fio_full;
    private String dolshnost;
    private String birth_date;
    private String age;
    private String sex;
    private String country;
    private String phone;
    private String e_mail;
    private String emp_type;
    private String okonchanie_instituta;
    private String dis_sovet_name;
    private String diss_qualification_name;
    private String fio_scientific_adviser;
    private String date_defense;
    private String time_defense;
    private String auditory;
    private String date_doc;
    private String date_admission;
    private String date_vak;
    private String date_minus_10;
    private String date_minus_5;

    private String date_plus_10;
    private String date_plus_15;
    private String date_plus_30;
    private String okso_ds;
    private String diss_spec_name_ds;
    private String theme;
    private String protocol_1;
    private String date_protocol_1;
    private String protocol_2;
    private String date_protocol_2;
    private String protocol_3;
    private String date_protocol_3;
    private String protocol_3_count_presents;
    private String protocol_3_count_voite;
    private String protocol_3_count_voite_not;

    private String protocol_3_count_voite_forgo;
    private String protocol_3_count_voite_bad;
    private String protocol_4;
    private String date_protocol_4;
    private String certification_case;
    private String certification_date;
    private String order_number;

    private String order_date;
    private String diplom_num;
    private String diplom_seria;
    private String url_vak;
    private String qr_code;
    private String comment;
    private String diss_status;
*/

}
