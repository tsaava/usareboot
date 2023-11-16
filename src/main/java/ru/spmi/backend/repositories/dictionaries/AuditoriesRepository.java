package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.dictionaries.DAuditories;

import java.util.ArrayList;

@Repository
public interface AuditoriesRepository extends JpaRepository<DAuditories, Integer> {


    ArrayList<DAuditories> findDAuditoriesByActiveAndSd(int active, int sd);

}