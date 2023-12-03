package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {
    //основная информация при открытии окна
    private int student_id;
    private String fio;
    private String birth_date;
    private String group_name;
    private String sex;
}
