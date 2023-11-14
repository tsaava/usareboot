package ru.spmi.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.FilterDTO;
import ru.spmi.backend.services.science.ScienceDAO;
import ru.spmi.backend.services.auth.UserDAO;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins=["*"], maxAge=3600)
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ScienceDAO scienceDAO;
//    @PreAuthorize("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<?> adminPage() {
        System.out.println("success login\nbro admin");
        return new ResponseEntity<>(new FilterDTO("success login\nbro admin"), HttpStatus.OK);
    }

//    @PostMapping("/filter")
//    public ResponseEntity<?> adminFilterAction(@RequestBody String filters) {
//        System.out.println(filters);
//        return new ResponseEntity<>(new Gson().toJson(userDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//    }

//    @PostMapping("/filter")
//    public ScienceTableDTO adminFilterAction(@RequestBody String filters,
//                                             @RequestParam(name="page_rows", required = true, defaultValue = "30") int page_rows,
//                                             @RequestParam(name="page_num", required = true, defaultValue = "0") int page_num) {
//        System.out.println(filters);
////        System.out.println(scienceDAO.getScienceAllJsonFromFilters(filters, 30, 0));
//        return scienceDAO.getScienceAllJsonFromFilters(filters, page_rows, page_num);
//    }

//
}
