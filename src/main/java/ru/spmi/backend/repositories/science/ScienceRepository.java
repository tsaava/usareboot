package ru.spmi.backend.repositories.science;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.dto.interfaces.sciense.ScienceDiplomResponse;
import ru.spmi.backend.dto.interfaces.sciense.ScienceListPersonsResponse;
import ru.spmi.backend.dto.interfaces.sciense.ScienceResponse;
import ru.spmi.backend.dto.interfaces.sciense.ScienceShedulesResponse;
import ru.spmi.backend.entities.auth.TestEntity;

import java.util.ArrayList;

@Repository
public interface ScienceRepository extends CrudRepository<TestEntity, Long> {


//    @Query(nativeQuery = true, value = "SELECT * FROM public.vf_test_complete_json_person(:filt::json, :count_rows, :num_row)")
//    ArrayList<?> paginationFunc(@Param("filt") String filt,@Param("count_rows") int count_rows,@Param("num_row") int num_row);

//    @Query(nativeQuery = true, value = "SELECT * FROM fn_increment(:num)")
//    Integer dbIncrement(Integer num);


//    cast(:filters AS json)//параметр который переводится в json
//@Query(nativeQuery = true, value = "SELECT req.fio AS fio, req.positions AS positions FROM public.vf_test_complete_json_person( cast(:filters AS json), :count_rows, :num_row) req")
//ArrayList<PaginationResponse> paginationFunc(@Param("filters") String filters, @Param("count_rows") int count_rows,@Param("num_row") int num_row);

//    @Query(nativeQuery = true, value = "SELECT req.* FROM public.vffp_journal_dissertations( cast(:filters AS json), :page_rows, :page_num) req")
//    ArrayList<ScienceResponse> scienceFunc(@Param("filters") String filters,
//                                              @Param("page_rows") int page_rows,
//                                              @Param("page_num") int page_num);

    /**
     * функция для заполнения журнала соискателей
     * @return JSON ScienceResponse
     */
    @Query(nativeQuery = true, value = "SELECT * FROM public.vf_journal_dissertations()")
    ArrayList<ScienceResponse> scienceFunc();

    /**
     * функция для заполнения формы по графикам дат (ScienceSchedule)
     * @param science_dis_id: long
     * @return JSON ScienceShedulesResponse
     */
    @Query(nativeQuery = true, value = "SELECT * FROM public.vf_science_dissertation_schedules(:science_dis_id)")
    ArrayList<ScienceShedulesResponse> scienceSchedulesFunc(@Param("science_dis_id")long science_dis_id);

    /**
     * процедура которая сохраняет введеные данные в базу
     * @param data: String
     * @return null
     */
    @Query(nativeQuery = true, value = "CALL public.science_dissertation_shedule_update(cast(:data AS json))")
    void scienceSchedulesUpdFunc(@Param("data")String data);

    /**
     * выводит список созданных дипломов у соискателей
     * @param year_id - учебный год
     */
    @Query(nativeQuery = true, value = " SELECT * FROM public.vf_science_diploms(:year_id)")
    ArrayList<ScienceDiplomResponse> scienceListDiplom(@Param("year_id") long year_id);

    /**
     * Функция, которая выдает список сотрудников и обучающихся аспирантов отфильтрованных по фамилии и имени из входного параметра
     * JSON filters и с учетом квалификации для обучающихся, причем 0 - все квалификации обучающихся (по умолчанию 1515)
     * @param filters - JSON {"filter_fname":"Иванов","filter_iname":"Иван"}
     * @param qual - 1515 - ищется только из аспирантов и сотрдуников
     * @return
     */
    @Query(nativeQuery = true, value = " SELECT * FROM public.vf_list_persons(cast(:filters AS json),:qual)")
    ArrayList<ScienceListPersonsResponse> scienceListPersons(@Param("filters") String filters,
                                                             @Param("qual") long qual);
}
