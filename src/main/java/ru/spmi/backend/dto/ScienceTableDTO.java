package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScienceTableDTO {
    private ArrayList<ScienceDTO> scienceList;
    private ArrayList<String> scienceFieldName;
    private ArrayList<String> scienceHeaderName;
    private int totalStudents;
    private int page_rows;
    private int page_num;
}
