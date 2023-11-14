package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.spmi.backend.dto.interfaces.StudentsResponse;
import ru.spmi.backend.entities.auth.TestEntity;

import java.util.ArrayList;

public interface StudentsRepository extends CrudRepository<TestEntity, Long> {
//    @Query(nativeQuery = true, value = "SELECT req.* FROM public.vffp_journal_students(:type_status, cast(:filters AS json), :person_user, :page_rows, :page_num) req")
//    ArrayList<StudentsResponse> studentFunc(@Param("type_status") int type_status,
//                                            @Param("filters") String filters,
//                                            @Param("person_user") long person_user,
//                                            @Param("page_rows") int page_rows,
//                                            @Param("page_num") int page_num);

    @Query(nativeQuery = true, value = "SELECT req.* FROM public.vffp_journal_students(:type_status, cast(:filters AS json), :person_user, :page_rows, :page_num) req")
    ArrayList<StudentsResponse> studentFunc(@Param("type_status") int type_status,
                                            @Param("filters") String filters,
                                            @Param("person_user") long person_user,
                                            @Param("page_rows") int page_rows,
                                            @Param("page_num") int page_num);
}
