package com.usareboot.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.usareboot.back.services.MainDAO;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/science/applicants")
@RequiredArgsConstructor
///"api/science"

public class ScienceCouncilController {
    @Autowired
    private MainDAO mainDAO;

    /**
     * Выдает список диссоветов
     * @param year (int) - календарный год
     * @return ResponseEntity<ListScienceCouncilDTO,HttpStatus>
     */
//    @GetMapping({"/sc/list/{year}"})
//    public ResponseEntity<?> getScienceCouncilList(@PathVariable int year) {
//        System.out.println(scienceCouncilDAO.getListScienceCouncil(year));
//        return new ResponseEntity<>(new Gson().toJson(scienceCouncilDAO.getListScienceCouncil(year)), HttpStatus.OK);
//    }
}