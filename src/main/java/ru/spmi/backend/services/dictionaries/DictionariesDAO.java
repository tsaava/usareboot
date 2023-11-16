package ru.spmi.backend.services.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.dto.auth.ChosenRoleDTO;
import ru.spmi.backend.dto.interfaces.dictionaries.DDegreeDetailsResponse;
import ru.spmi.backend.entities.auth.DRolesEntity;
import ru.spmi.backend.entities.dictionaries.DAuditories;
import ru.spmi.backend.entities.dictionaries.DDegreeDetails;
import ru.spmi.backend.entities.dictionaries.DScienceApplicantStatuses;
import ru.spmi.backend.repositories.dictionaries.AuditoriesRepository;
import ru.spmi.backend.repositories.dictionaries.DegreeDetailsRepository;
import ru.spmi.backend.repositories.dictionaries.ScienceApplicantStatusesRepository;

import java.util.ArrayList;
import java.util.Set;

@Service
public class DictionariesDAO {
    @Autowired
    private AuditoriesRepository auditoriesRepository;

    @Autowired
    private ScienceApplicantStatusesRepository scienceApplicantStatusesRepository;

    @Autowired
    private DegreeDetailsRepository degreeDetailsRepository;

    /**
     * метод для запроса списка аудиторий
     * @return DAuditories
     */
    public ArrayList<DAuditories> getListAuditories( ) {
        return auditoriesRepository.findDAuditoriesByActiveAndSd(1, 1);
    }

    /**
     * метод для запроса списка статусов соискателя
     * @return DScienceApplicantStatuses
     */
    public ArrayList<DScienceApplicantStatuses> getListScienceApplicantStatuses( ) {
        return scienceApplicantStatusesRepository.findDScienceApplicantStatusesByActive(1);
    }

    /**
     * метод для запроса списка степеней
     *
     * @return DDegreeDetails
     */
    public ArrayList<DDegreeDetails> getListDegreeDetails( ) {
        //        System.out.println(degreeDetailsArray);
        return degreeDetailsRepository.findDDegreeDetailsByActive(1);
    }


}
