package ru.spmi.backend.repositories;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.dto.interfaces.MenuResponse;
import ru.spmi.backend.dto.interfaces.ScienceResponse;
import ru.spmi.backend.entities.TestEntity;
import ru.spmi.backend.entities.UsersEntity;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface MenuRepository extends CrudRepository<TestEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT public.role_menu( :roles)")
    Map menuFunc(@Param("roles") long roles);
}
