package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.dto.ItemsRequestDTO;
import com.usareboot.back.services.MainDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot")
@RequiredArgsConstructor
///"api/science"

public class MainController {
    @Autowired
    private MainDAO mainDAO;


    @PostMapping ("/import/data/{albom}")
    public ResponseEntity<?> importData(@PathVariable String albom,
                                        @RequestBody String filters) {
        System.out.print(albom+' '+filters);
        albom=albom.replace("\"","");
        mainDAO.getImportList(filters, albom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping ("/import/list")
    public ResponseEntity<?> importList( @RequestBody String listAlbom) {
        System.out.println("listAlbom: "+listAlbom);
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getListImport(listAlbom)), HttpStatus.OK);
    }


    @GetMapping("/item/list")
    public ResponseEntity<?> getItemList() {
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @GetMapping("/item/statuses/{type}")
    public ResponseEntity<?> getItemStatuses(@PathVariable int type) {
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getStatusesItem(type)), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}")
    public void patchItemWeightStatus(@PathVariable long id,
                                      @RequestBody String data) {
        System.out.println(data);
        Gson g = new Gson();
        mainDAO.saveItemWeightAndStatus(id,g.fromJson(data,ItemsRequestDTO.class));
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @PatchMapping("/item/set/date/all")
    public void patchItemDate() {
        mainDAO.setItemDate();
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }




//    @PostMapping("/all")
//    public ResponseEntity<?> adminFilterAction(@RequestParam(name="page_rows",  defaultValue = "30") int page_rows,
//                                               @RequestParam(name="page_num",  defaultValue = "0") int page_num,
//                                               @RequestBody String filters){
////        System.out.println(filters);
////        System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters(filters,  page_rows, page_num)), HttpStatus.OK);
//    }

//    @GetMapping("/applicants")
//    public ResponseEntity<?> adminFilterAction(){
////        System.out.println(filters);
//        //System.out.println(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceAllJsonFromFilters()), HttpStatus.OK);
//    }
//
//    /**
//     * Отправляет информацию по графику дат для соискателя
//     * @param id - science_dissertation_id
//     * @return ResponseEntity<ScienceSchedulesDTO,HttpStatus>
//     */
//    @GetMapping({"/applicants/{id}/schedules/"})
//    public ResponseEntity<?> scienceScheduleShow(@PathVariable int id){
////        System.out.println(filters);
//        System.out.println(new Gson().toJson(scienceDAO.getScienceSchedulesJson((id))));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceSchedulesJson(id)), HttpStatus.OK);
//    }
//
//    /**
//     * Обновляет график дат с грида! (через БД. передается json с тремя полями (ид строки и две даты в формате yyyy-mm-dd) парситься и обновляется процедурой)
//     * @param data - собранный в JSON грид графика дат
//     * @param id - science_dissertation_id
//     * @return возвращает новые данные по выбранному соискателю
//     */
//    @PostMapping({"/applicants/{id}/schedules/update/grid"})
//    public ResponseEntity<?> scienceScheduleControlDatesUpdate(@RequestBody String data,@PathVariable int id  ) {
//       scienceDAO.scienceSchedulesUpdFunc(data);
//        return new ResponseEntity<>( HttpStatus.OK);
//    }
//
//    /**
//     * метод для обновления переданных данных с фронта в таблице  science_dissertation_schedules
//     * @param scienceDateRequestDTO - поля для сохранения в таблицу
//     * @param id - science_dissertation_id
//     * @return ResponseEntity<HttpStatus>
//     */
//    @PatchMapping({"/applicants/{id}/schedules/update/date"})
//    public ResponseEntity<?> scienceScheduleInfoUpdate(@RequestBody ScienceDateRequestDTO scienceDateRequestDTO,@PathVariable int id) {
//        System.out.println(scienceDateRequestDTO);
//        ScienceDissertations sd = scienceDissertationsRepository.findById(id);
//        //sd.setScienceDissertationId(id);
//        sd.setDateDefense(scienceDateRequestDTO.getDateDefense());
//        sd.setDateDocument(scienceDateRequestDTO.getDateDog());
//        sd.setTimeDefense(Time.valueOf(LocalTime.parse(scienceDateRequestDTO.getTimeDefense())));
////        sd.getAuditoryId()
//        scienceDissertationsRepository.save(sd);
//        return new ResponseEntity<>( HttpStatus.OK);//new ResponseEntity<>(new Gson().toJson(scienceDAO.getEmployersJsonFromFilters(filters, 30, 0)), HttpStatus.OK);
//    }
//
//
//    /**
//     *Функция для выдачи списка созданных дипломов соискателей (функция vf_science_diploms() )
//     * @param year_id - id учебного года
//     * @return ResponseEntity<ScienceDiplomDTO,HttpStatus>
//     */
//    @GetMapping("/diplom/list/{year_id}")
//    public ResponseEntity<?> getdiplomList(@PathVariable long year_id){
////        System.out.println(filters);
//        //System.out.println(new Gson().toJson(scienceDAO.getScienceDiplomas(year_id)));
//        return new ResponseEntity<>(new Gson().toJson(scienceDAO.getScienceDiplomas(year_id)), HttpStatus.OK);
//    }


}