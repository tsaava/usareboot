package ru.spmi.backend.repositories.dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.dictionaries.DAuditories;

@Repository
public interface AuditoriesRepository extends JpaRepository<DAuditories, Integer> {


    DAuditories getAllBy();

}