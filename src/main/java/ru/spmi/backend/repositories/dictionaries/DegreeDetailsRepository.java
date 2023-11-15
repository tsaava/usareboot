package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.dictionaries.DDegreeDetails;

@Repository
public interface DegreeDetailsRepository extends JpaRepository<DDegreeDetails, Integer> {

    DDegreeDetails getAllBy();

}