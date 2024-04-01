package com.usareboot.back.services.science;

import com.usareboot.back.repositories.science.ScienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SciencePersonalInfoDAO {
    @Autowired
    private ScienceRepository scienceRepository;
//    public void scienceSchedulesUpdFunc(String data) {
//        scienceRepository.scienceSchedulesUpdFunc(data);
//    }
    public  void sciencePersonalInfoUpdFunc(String data){

    }
//    public ArrayList<ScienceListPersonsDTO> getScienceListPersons(String filters, long qual) {
//        ArrayList<ScienceListPersonsDTO> scienceListPersons = new ArrayList<>();
//        var bdFuncResponse = scienceRepository.scienceListPersons(filters, qual);
//        if (bdFuncResponse.size() > 0) {
//            bdFuncResponse.forEach(x -> scienceListPersons.add(new ScienceListPersonsDTO(
//                    x.getperson_id(),
//                    x.getfname() != null ? x.getfname().toString() : "",
//                    x.getiname() != null ? x.getiname() : "",
//                    x.getoname() != null ? x.getoname() : "",
//                    x.getsex() != null ? x.getsex() : "",
//                    x.getbirth_date() != null ? x.getbirth_date() : "",
//                    x.getcountry() != null ? x.getcountry() : "",
//                    x.getperson_status() != null ? x.getperson_status() : ""
//                 )));
//        }
//        return scienceListPersons;
//    }



}
