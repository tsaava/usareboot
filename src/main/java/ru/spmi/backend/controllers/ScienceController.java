package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.ScienceTableDTO;
import ru.spmi.backend.dto.sciense.ScienceDateRequestDTO;
import ru.spmi.backend.entities.sciense.ScienceDissertations;
import ru.spmi.backend.repositories.ScienceDissertationsRepository;
import ru.spmi.backend.services.ScienceDAO;

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

    @GetMapping("/applicants")
    public ResponseEntity<?> adminFilterAction(){
//        System.out.println(filters);
        //System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()), HttpStatus.OK);
    }

    @GetMapping({"/applicants/{id}/schedules/"})
    public ResponseEntity<?> scienceScheduleShow(@PathVariable int id){
//        System.out.println(filters);
        System.out.println(new Gson().toJson(scienceDAO.getScienceSchedulesJson((id))));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceSchedulesJson(id)), HttpStatus.OK);
    }

    /**
     * обновляет график дат с грида! (через БД. передается json с тремя полями (ид строки и две даты в формате yyyy-mm-dd) парситься и обновляется процедурой)
     * @param data
     * @param id
     * @return
     */
    @PostMapping({"/applicants/{id}/schedules/update/grid"})
    public ResponseEntity<?> scienceScheduleControlDatesUpdate(@RequestBody String data,@PathVariable int id  ) {
       scienceDAO.scienceSchedulesUpdFunc(data);
        return new ResponseEntity<>( HttpStatus.OK);
    }


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
//
//    @PostMapping("/filter")
//    public ResponseEntity<?> adminFilterAction(@RequestBody String filters) {
//        System.out.println(filters);
//        //return new ResponseEntity<>(new Gson().toJson(scienceDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//
//    }

    @GetMapping("/diplomas")
    public ResponseEntity<?> diplomList(){
//        System.out.println(filters);
        //System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()), HttpStatus.OK);
    }
}