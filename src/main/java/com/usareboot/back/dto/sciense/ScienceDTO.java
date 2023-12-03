package com.usareboot.back.dto.sciense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceDTO {
    //основная информация при открытии окна
    private String personId;
    private String scienceDissertationId;
    private String scienceCouncilSpecId;
    private String degreeDetailId;
    private String scienceApplicantStatusId;
    private String scienceDissertationStatusId;
    private String employeePositionId;
    private String auditoryId;
    private String orderId;
    private String specId;

    private String fioFull;
    private String okonchanieInstituta;
    private String disSovetName;
    private String dissQualificationName;
    private String degreeDetailShort;
    private String dateDefense;
    private String timeDefense;
    private String auditory;

    private String dateAdmission;
    private String dateVak;
    private String dateMinus10;
    private String dateMinus5;
    private String datePlus10;
    private String datePlus15;
    private String datePlus30;

    private String fioScientificAdviser;
    private String oksoDs;
    private String dissSpecNameDs;
    private String theme;
    private String dissStatus;

    //    информация при просмотре детализации
    private String dateDoc;
    private String protocol1;
    private String dateProtocol1;
    private String protocol2;
    private String dateProtocol2;
    private String protocol3;
    private String dateProtocol3;
    private String certificationCase;
    private String certificationDate;
    private String protocol4;
    private String dateProtocol4;

    private String orderNumber;
    private String orderDate;
    private String diplomNum;
    private String diplomSeria;
    private String urlVakInfo;
    private String comment;

    //    информация о соискателе (общая)
    private String dolshnost;
    private String birthDate;
    private String phone;
    private String eMail;
    private String age;
    private String sex;
    private String country;
    private String empType;

    private String qrCode;
    private String protocol3CountPresents;
    private String protocol3CountVoite;
    private String protocol3CountVoiteNot;

    private String protocol3CountVoiteForgo;
    private String protocol3CountVoiteBad;

    private String flagDateMinus10;
    private String flagDateMinus5;
    private String flagDatePlus10;
    private String flagDatePlus15;
    private String flagDatePlus30;
    private String fioShort;

    private String applicantStatus;
    private String fNameEng;
    private String iNameEng;
    private String oNameEng;
    private String themeEng;
    private String keyword;
    private String keywordEng;
    private String urlApplicant;
    private String partText;
    private String contacts;

    private String fName;
    private String iName;
    private String oName;
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
