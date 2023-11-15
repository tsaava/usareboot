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

import java.sql.Time;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/science")
@RequiredArgsConstructor
///"api/science"

public class ScienceController {
    @Autowired
    private ScienceDAO scienceDAO;

    private final ScienceDissertationsRepository scienceDissertationsRepository;
//    @GetMapping("/all")
//    public ResponseEntity<?> scienceAllPage(@RequestBody String filters) {
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//    }

//    @PostMapping("/all")
//    public ResponseEntity<?> adminFilterAction(@RequestParam(name="page_rows",  defaultValue = "30") int page_rows,
//                                               @RequestParam(name="page_num",  defaultValue = "0") int page_num,
//                                               @RequestBody String filters){
////        System.out.println(filters);
////        System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)), HttpStatus.OK);
//    }

//    @GetMapping("/applicants")
//    public ResponseEntity<?> adminFilterAction(){
////        System.out.println(filters);
//        //System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()), HttpStatus.OK);
//    }

    /**
     * Отправляет информацию по графику дат для соискателя
     * @param id - science_dissertation_id
     * @return ResponseEntity<ScienceSchedulesDTO,HttpStatus>
     */
    @GetMapping({"/applicants/{id}/schedules/"})
    public ResponseEntity<?> scienceScheduleShow(@PathVariable int id){
//        System.out.println(filters);
        System.out.println(new Gson().toJson(scienceDAO.getScienceSchedulesJson((id))));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceSchedulesJson(id)), HttpStatus.OK);
    }

    /**
     * Обновляет график дат с грида! (через БД. передается json с тремя полями (ид строки и две даты в формате yyyy-mm-dd) парситься и обновляется процедурой)
     * @param data - собранный в JSON грид графика дат
     * @param id - science_dissertation_id
     * @return возвращает новые данные по выбранному соискателю
     */
    @PostMapping({"/applicants/{id}/schedules/update/grid"})
    public ResponseEntity<?> scienceScheduleControlDatesUpdate(@RequestBody String data,@PathVariable int id  ) {
       scienceDAO.scienceSchedulesUpdFunc(data);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    /**
     * метод для обновления переданных данных с фронта в таблице  science_dissertation_schedules
     * @param scienceDateRequestDTO - поля для сохранения в таблицу
     * @param id - science_dissertation_id
     * @return ResponseEntity<HttpStatus>
     */
    @PatchMapping({"/applicants/{id}/schedules/update/date"})
    public ResponseEntity<?> scienceScheduleInfoUpdate(@RequestBody ScienceDateRequestDTO scienceDateRequestDTO,@PathVariable int id) {
        System.out.println(scienceDateRequestDTO);
        ScienceDissertations sd = scienceDissertationsRepository.findById(id);
        //sd.setScienceDissertationId(id);
        sd.setDateDefense(scienceDateRequestDTO.getDate_defense());
        sd.setDateDocument(scienceDateRequestDTO.getDate_dog());
        sd.setTimeDefense(Time.valueOf(LocalTime.parse(scienceDateRequestDTO.getTime_defense())));
//        sd.getAuditoryId()
        scienceDissertationsRepository.save(sd);
        return new ResponseEntity<>( HttpStatus.OK);//new ResponseEntity<>(new Gson().toJson(scienceDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
    }


    /**
     *Функция для выдачи списка созданных дипломов соискателей (функция vf_science_diploms() )
     * @param year_id - id учебного года
     * @return ResponseEntity<ScienceDiplomDTO,HttpStatus>
     */
    @GetMapping("/diplom/list/{year_id}")
    public ResponseEntity<?> getdiplomList(@PathVariable long year_id){
//        System.out.println(filters);
        //System.out.println(new Gson().toJson(scienceDAO.getScienceDiplomas(year_id)));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceDiplomas(year_id)), HttpStatus.OK);
    }


}