package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.dto.ScienceDTO;
import ru.spmi.backend.dto.ScienceTableDTO;
import ru.spmi.backend.dto.StudentDTO;
import ru.spmi.backend.repositories.ScienceRepository;
import ru.spmi.backend.repositories.StudentsRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class StudentDAO {

    @Autowired
    private StudentsRepository studentsRepository;

    public ArrayList<StudentDTO> getStudentsAllJsonFromFilters(int type_status, String filters, long pers_id, int page_rows, int page_num) {
        ArrayList<StudentDTO> list = new ArrayList<>();
        var bdFuncResponse = studentsRepository.studentFunc(type_status,filters,pers_id, page_rows, page_num);
        if (bdFuncResponse.size() > 0) {
            bdFuncResponse.forEach(x -> list.add(new StudentDTO(
                    x.getstudent_id(),
                    x.getfio(),
                    x.getbirth_date(),
                    x.getgroup_name(),
                    x.getsex()
            )));

            var fields = new ArrayList<>(Arrays.asList(ScienceDTO.class.getDeclaredFields()));
            ArrayList<String> fieldsList = new ArrayList<>();
            fields.forEach(x -> fieldsList.add(x.getName()));

            return list;//scienceTable;
        } else
            return null;
    }
}
