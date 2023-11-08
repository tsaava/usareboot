package ru.spmi.backend.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.sciense.ScienceDissertations;

@Repository
public interface ScienceDissertationsRepository extends JpaRepository<ScienceDissertations, Integer> {


    ScienceDissertations findById(int id);

}