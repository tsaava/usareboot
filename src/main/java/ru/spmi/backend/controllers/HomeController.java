package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.repositories.science.ScienceRepository;
import ru.spmi.backend.repositories.auth.UserRepository;
import ru.spmi.backend.services.auth.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScienceRepository scienceRepository;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/")
    public ResponseEntity<?> getHomePage() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }
//
    @PostMapping("/test")
    public ResponseEntity<?> testMethod(@RequestBody String filters) {

        return new ResponseEntity<>(filters, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {

       // var arr = testRepository.paginationFunc("{\"filter_fio\":\"Ива\", \"filter_position\":\"\"}", 30, 0);
//        for (var a : arr) {
//            System.out.println(a.getFio());
//        }
//        System.out.println("db test fun call(6) = " + testRepository.dbIncrement(6));
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
//    @GetMapping("/success")
//    public ResponseEntity<?> homeSuccess() {
//        return new ResponseEntity<>(new ChosenRoleDTO("success"), HttpStatus.OK);
//    }

}
