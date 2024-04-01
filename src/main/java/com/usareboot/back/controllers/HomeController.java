package com.usareboot.back.controllers;

import com.usareboot.back.repositories.auth.UserRepository;
import com.usareboot.back.repositories.science.ScienceRepository;
import com.usareboot.back.services.auth.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/home")
    public ResponseEntity<?> getHomePage() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }
//
    @PostMapping("/vk/server")
    public String VkServer(/*@RequestBody String json*/) {
//        if (json.contains("224336762"))
            return "98639192";
//        else
//            return null;
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
