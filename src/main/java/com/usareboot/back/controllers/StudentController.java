package com.usareboot.back.controllers;

import com.usareboot.back.dto.FilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usareboot.back.services.StudentDAO;

@RestController
@RequestMapping("/api/university/student")
public class StudentController {
    @Autowired
    private StudentDAO studentDAO;
    @GetMapping("/home")
    public ResponseEntity<?> studentHomePage() {
        return new ResponseEntity<>(new FilterDTO("У студентов стипуха маленькая, а так все отлично"), HttpStatus.OK);
    }
//    @PostMapping("/all")
//    public ResponseEntity<?> studentAll(@RequestParam(name="page_rows",  defaultValue = "30") int page_rows,
//                                               @RequestParam(name="page_num",  defaultValue = "0") int page_num,
//                                        @RequestParam(name="person_user",  defaultValue = "63") long person_user,
//                                        @RequestParam(name="type_status",  defaultValue = "1") int type_status,
//                                        @RequestBody String filters){
////        System.out.println(filters);
////        System.out.println(new Gson().toJson(studentDAO.getStudentsAllJsonFromFilters(type_status, filters, person_user,  page_rows, page_num)));
//        return new ResponseEntity<>(new Gson().toJson(studentDAO.getStudentsAllJsonFromFilters(type_status,filters, person_user, page_rows, page_num)), HttpStatus.OK);
//    }
}
