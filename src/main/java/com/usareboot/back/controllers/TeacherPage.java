package com.usareboot.back.controllers;

import com.usareboot.back.dto.FilterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherPage {

    @GetMapping("/home")
    public ResponseEntity<?> teacherPage() {
        System.out.println("success teacher visit");
        return new ResponseEntity<>(new FilterDTO("success teacher br br br"), HttpStatus.OK);

    }
}
