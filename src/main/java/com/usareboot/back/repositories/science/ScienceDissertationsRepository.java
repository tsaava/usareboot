package com.usareboot.back.repositories.science;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usareboot.back.entities.sciense.ScienceDissertations;

@Repository
public interface ScienceDissertationsRepository extends JpaRepository<ScienceDissertations, Integer> {


    ScienceDissertations findById(int id);

}