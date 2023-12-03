package com.usareboot.back.repositories.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.usareboot.back.entities.auth.TestEntity;

@Repository
public interface MenuRepository extends CrudRepository<TestEntity, Long> {

//    @Query(nativeQuery = true, value = "SELECT public.role_menu( :roles)")
//    Map menuFunc(@Param("roles") long roles);
}
