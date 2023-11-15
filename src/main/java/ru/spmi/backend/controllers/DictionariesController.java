package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.services.dictionaries.AuditorieslDAO;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/dictionaries")
@RequiredArgsConstructor
///"api/science"

public class DictionariesController {
    @Autowired
    private AuditorieslDAO auditorieslDAO;


    @GetMapping({"rooms/list"})
    public ResponseEntity<?> getScienceCouncilList() {
        System.out.println(auditorieslDAO.getListAuditories());
        return new ResponseEntity<>(new Gson().toJson(auditorieslDAO.getListAuditories()), HttpStatus.OK);
    }
}