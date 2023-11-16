package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.dictionaries.DDegreeDetails;
import ru.spmi.backend.entities.dictionaries.DScienceApplicantStatuses;

import java.util.ArrayList;

@Repository
public interface ScienceApplicantStatusesRepository extends JpaRepository<DScienceApplicantStatuses, Integer> {


    ArrayList<DScienceApplicantStatuses>  findDScienceApplicantStatusesByActive(int active);

}