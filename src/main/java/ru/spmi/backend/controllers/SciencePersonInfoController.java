package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.sciense.ScienceDateRequestDTO;
import ru.spmi.backend.entities.sciense.ScienceDissertations;
import ru.spmi.backend.repositories.science.ScienceDissertationsRepository;
import ru.spmi.backend.services.science.ScienceDAO;
import ru.spmi.backend.services.science.SciencePersonalInfoDAO;

import java.sql.Time;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/science/applicants/personal/info")
@RequiredArgsConstructor
///"api/science"

public class SciencePersonInfoController {
    @Autowired
    private SciencePersonalInfoDAO sciencePersonalInfoDAO;

    /**
     * Метод принимает данные в виде json с фронта и запускает процедуру на обновление данных по анкете соискателя
     * @param data - JSON с информацией о соискателе (форма анкета соискателя) JSON входной параметр у функции ..
     * @param id - может быть два варианта: science_dissertation_id=0 - добавляем нового соискателя, science_dissertation_id!=0 - редактируем
     * @return возвращает новые данные по выбранному соискателю
     */
    @PatchMapping({"/update/{id}"})
    public ResponseEntity<?> sciencePersonalInfoUpdate(@RequestBody String data,@PathVariable int id  ) {
        //System.out.println(data);
        sciencePersonalInfoDAO.sciencePersonalInfoUpdFunc(data);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping({"/create"})
    public ResponseEntity<?> sciencePersonalInfoCreate(@RequestBody String data ) {
        //System.out.println(data);
        sciencePersonalInfoDAO.sciencePersonalInfoUpdFunc(data);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    /**
     * Выводит список сотрудников
     * @param filters (String)- JSON с двумя полями: фамилия и имя - по ним фильтруется информация
     * @param qual (long) - квалификация для обучающихся, 0 - все квалификации обучающихся. По умолчанию 1515 (аспиранты + сотрудники)
     * @return возвращает новые данные по выбранному соискателю
     */
    @PostMapping({"/list/pers/{qual}"})
    public ResponseEntity<?> getDiplomList(@RequestBody String filters,@PathVariable long qual ) {
        //System.out.println(filters);
        System.out.println(sciencePersonalInfoDAO.getScienceListPersons(filters,qual));
        return new ResponseEntity<>(new Gson().toJson(sciencePersonalInfoDAO.getScienceListPersons(filters,qual)), HttpStatus.OK);
    }
}