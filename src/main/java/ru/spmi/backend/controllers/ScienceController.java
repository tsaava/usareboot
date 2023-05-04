package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.ScienceTableDTO;
import ru.spmi.backend.services.ScienceDAO;

@RestController
@RequestMapping("/api/science")

public class ScienceController {
    @Autowired
    private ScienceDAO scienceDAO;

//    @GetMapping("/all")
//    public ResponseEntity<?> scienceAllPage(@RequestBody String filters) {
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//    }

    @PostMapping("/all")
    public ResponseEntity<?> adminFilterAction(@RequestBody String filters,
                                             @RequestParam(name="page_rows",  defaultValue = "30") int page_rows,
                                             @RequestParam(name="page_num",  defaultValue = "0") int page_num) {
        System.out.println(filters);
        System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)));
        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)), HttpStatus.OK);
    }
//
//    @PostMapping("/filter")
//    public ResponseEntity<?> adminFilterAction(@RequestBody String filters) {
//        System.out.println(filters);
//        //return new ResponseEntity<>(new Gson().toJson(scienceDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//
//    }
}
