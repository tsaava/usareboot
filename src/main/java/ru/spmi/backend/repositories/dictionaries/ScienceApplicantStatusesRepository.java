package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.dictionaries.DScienceApplicantStatuses;

@Repository
public interface ScienceApplicantStatusesRepository extends JpaRepository<DScienceApplicantStatuses, Integer> {


    DScienceApplicantStatuses getAllBy();

}