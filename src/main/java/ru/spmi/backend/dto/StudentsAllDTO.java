package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class StudentsAllDTO {

    private String reg_num;
    private String fio;
    private String birth_date;
    private String group_name;
    private String sex;



/*
    @Schema(description = "ФИО студента")
    //@Size(min = 3, max = 30)

    var fio:String?,

    @Schema(description = "Дата рождения студента")
    //@Size(min = 3, max = 30)

    var birth_date:String?,

    @Schema(description = "Группа")

    var group_name:String?,

    @Schema(description = "Пол студента")
    var sex:String?,

    @Schema(description = "Курс студента")
    var kurs:String?,

    @Schema(description = "Специальность студента")
    var spec_name:String?,

    @Schema(description = "Специализация студента")
    var qualification_name:String?,

    @Schema(description = "Форма обучения студента")
    var fo_name:String?,

    @Schema(description = "Факультет студента")
    var fac_code:String?,


    @Schema(description = "Гражданство студента")
    var name_stat:String?,

    @Schema(description = "Форма обучения студента")
    var compensation_type_code:String?,

    @Schema(description = "Форма обучения студента")
    var student_id:Long?,*/
}
