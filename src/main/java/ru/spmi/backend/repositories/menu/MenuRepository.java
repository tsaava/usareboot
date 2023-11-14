package ru.spmi.backend.repositories.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.auth.TestEntity;

import java.util.Map;

@Repository
public interface MenuRepository extends CrudRepository<TestEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT public.role_menu( :roles)")
    Map menuFunc(@Param("roles") long roles);
}
