package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceDTO {
    //основная информация при открытии окна
    private String _02fio_full;
    private String _03okonchanie_instituta;
    private String _04dis_sovet_name;
    private String _05diss_qualification_name;
    private String _06date_defense;
    private String _07time_defense;
    private String _08auditory;

    private String _09date_admission;
    private String _10date_vak;
    private String _1z1date_minus_10;
    private String _1z2date_minus_5;
    private String _1z3date_plus_10;
    private String _1z4date_plus_15;
    private String _1z5date_plus_30;

    private String _1z6fio_scientific_adviser;
    private String _1z7okso_ds;
    private String _1z8diss_spec_name_ds;
    private String _1z9theme;
    private String _2z0diss_status;

    //    информация при просмотре детализации
    private String _2z1date_doc_details;
    private String _2z2protocol_1_details;
    private String _2z3date_protocol_1_details;
    private String _2z4protocol_2_details;
    private String _2z5date_protocol_2_details;
    private String _2z6protocol_3_details;
    private String _2z7date_protocol_3_details;
    private String _2z8certification_case_details;
    private String _2z9certification_date_details;
    private String _3z0protocol_4_details;
    private String _3z1date_protocol_4_details;

    private String _3z2order_number_details;
    private String _3z3order_date_details;
    private String _3z4diplom_num_details;
    private String _3z5diplom_seria_details;
    private String _3z6url_vak_info;
    private String _3z7comment_details;

    //    информация о соискателе (общая)
    private String _3z8dolshnost_info;
    private String _3z9birth_date_info;
    private String _4z0phone_info;
    private String _4z1e_mail_info;
    private String _4z2age_info;
    private String _4z3sex_info;
    private String _4z4country_info;
    private String _4z5emp_type_info;

    private String _4z6qr_code_info;
    private String _4z7protocol_3_count_presents_info;
    private String _4z8protocol_3_count_voite_info;
    private String _4z9protocol_3_count_voite_not_info;

    private String _5z0protocol_3_count_voite_forgo_info;
    private String _5z1protocol_3_count_voite_bad_info;



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
