package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.services.science.ScienceCouncilDAO;
import ru.spmi.backend.services.science.SciencePersonalInfoDAO;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/science")
@RequiredArgsConstructor
///"api/science"

public class ScienceCouncilController {
    @Autowired
    private ScienceCouncilDAO scienceCouncilDAO;


    @GetMapping({"/sc/list/{year}"})
    public ResponseEntity<?> getScienceCouncilList(@PathVariable long year) {
        System.out.println(scienceCouncilDAO.getListScienceCouncil(year));
        return new ResponseEntity<>(new Gson().toJson(scienceCouncilDAO.getListScienceCouncil(year)), HttpStatus.OK);
    }
}