package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.auth.UsersEntity;
import ru.spmi.backend.entities.dictionaries.DDegreeDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface DegreeDetailsRepository extends JpaRepository<DDegreeDetails, Integer> {

//    Optional<DDegreeDetails> findDDegreeDetailsByActive(int active);
    ArrayList<DDegreeDetails> findDDegreeDetailsByActive(int active);

}