package ru.spmi.backend.services.science;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.dto.sciense.ListScienceCouncilDTO;
import ru.spmi.backend.dto.sciense.ScienceListPersonsDTO;
import ru.spmi.backend.repositories.science.ScienceRepository;

import java.util.ArrayList;

@Service
public class ScienceCouncilDAO {
    @Autowired
    private ScienceRepository scienceRepository;

    public ArrayList<ListScienceCouncilDTO> getListScienceCouncil(long year ) {
        ArrayList<ListScienceCouncilDTO> scienceCouncilList = new ArrayList<>();
        var bdFuncResponse = scienceRepository.listScienceCouncil(year);
        if (bdFuncResponse.size() > 0) {
            bdFuncResponse.forEach(x -> scienceCouncilList.add(new ListScienceCouncilDTO(
                    x.getscience_council_spec_id(),
                    x.getscience_council_id(),
                    x.getspec_id(),
                    x.getorder_id(),
                    x.getorder_number() != null ? x.getorder_number() : "",
                    x.getorder_date() != null ? x.getorder_date() : "",
                    x.getscience_council_name() != null ? x.getscience_council_name() : "",
                    x.getdate_from() != null ? x.getdate_from() : "",
                    x.getdate_to() != null ? x.getdate_to() : "",
                    x.getspec_name() != null ? x.getspec_name() : "",
                    x.getphone() != null ? x.getphone() : "",
                    x.gete_mail() != null ? x.gete_mail() : ""
                 )));
        }
        return scienceCouncilList;
    }



}
