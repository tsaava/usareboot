package ru.spmi.backend.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spmi.backend.dto.auth.ChosenRoleDTO;
import ru.spmi.backend.dto.dictionaries.DAuditoriesDTO;
import ru.spmi.backend.dto.dictionaries.DDegreeDetailsDTO;
import ru.spmi.backend.dto.dictionaries.DScienceApplicantStatusesDTO;
import ru.spmi.backend.services.dictionaries.DictionariesDAO;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/university/dictionaries")
@RequiredArgsConstructor
///"api/science"

public class DictionariesController {
    @Autowired
    private DictionariesDAO dictionariesDAO;


    @GetMapping({"/rooms/list"})
    public ResponseEntity<?> getScienceCouncilList() {
        var list = dictionariesDAO.getListAuditories();
        ArrayList<DAuditoriesDTO> roleList = new ArrayList<>();
        list.forEach(x -> roleList.add(new DAuditoriesDTO(
                x.getAuditoryId(),
                x.getEducationCenterId(),
                x.getBuildingId(),
                x.getAuditoryTypeId(),
                x.getAuditoryCode(),
                x.getAuditoryName(),
                x.getSd(),
                x.getActive())));
        System.out.println(new Gson().toJson(list));
        return new ResponseEntity<>(new Gson().toJson(list), HttpStatus.OK);
    }

    @GetMapping({"/science/applicant/status/list"})
    public ResponseEntity<?> getScienceApplicantStatusesList() {
        var list = dictionariesDAO.getListScienceApplicantStatuses();
        ArrayList<DScienceApplicantStatusesDTO> roleList = new ArrayList<>();
//        list.forEach(x -> roleList.add(new DAuditoriesDTO( x.)));
        System.out.println(new Gson().toJson(list));

        list.forEach(x -> roleList.add(new DScienceApplicantStatusesDTO(
                x.getScienceApplicantStatusId(),
                x.getStatusShort(),
                x.getStatusName(),
                x.getActive()
               )));
        System.out.println(new Gson().toJson(list));
        return new ResponseEntity<>(new Gson().toJson(list), HttpStatus.OK);
    }

    @GetMapping({"/degree/details/list"})
    public ResponseEntity<?> getDegreeDetailsList() {
//        System.out.println(dictionariesDAO.getListDegreeDetails());
       var list = dictionariesDAO.getListDegreeDetails();
        ArrayList<DDegreeDetailsDTO> roleList = new ArrayList<>();
        list.forEach(x -> roleList.add(new DDegreeDetailsDTO(
                x.getDegreeDetailId(),
                x.getDegreeDetailCode(),
                x.getDegreeDetailShort(),
                x.getDegreeDetailName(),
                x.getScience(),
                x.getScienceShort(),
                x.getScienceEng(),
                x.getActive())));
        System.out.println(new Gson().toJson(list));
//        ArrayList<DDegreeDetailsDTO> ddList = new ArrayList<>();
//        list.forEach(x -> ddList.add(new DDegreeDetailsDTO(   x.getdegreeDetailId(),
//                x.getRoleName(),
//                x.getRoleCode())));
       return new ResponseEntity<>((new Gson().toJson(list)), HttpStatus.OK);
    }

}