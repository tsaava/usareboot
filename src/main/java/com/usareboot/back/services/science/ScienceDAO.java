package com.usareboot.back.services.science;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usareboot.back.persistence.usareboot.repository.science.ScienceRepository;

@Service
public class ScienceDAO {
    @Autowired
    private ScienceRepository scienceRepository;

//    public ArrayList<ScienceDTO> getScienceAllJsonFromFilters(/*String filters, int page_rows, int page_num*/) {
//        ArrayList<ScienceDTO> scienceList = new ArrayList<>();
//        var bdFuncResponse = scienceRepository.scienceFunc();
//        if (bdFuncResponse.size() > 0) {
//            bdFuncResponse.forEach(x -> scienceList.add(new ScienceDTO(
//                    x.getperson_id()!=null? x.getperson_id().toString() : "",
//                    x.getscience_dissertation_id()!=null? x.getscience_dissertation_id().toString() : "",
//                    x.getscience_council_spec_id()!=null? x.getscience_council_spec_id().toString() : "",
//                    x.getdegree_detail_id()!=null? x.getdegree_detail_id().toString() : "",
//                    x.getscience_applicant_status_id()!=null? x.getscience_applicant_status_id().toString() : "",
//                    x.getscience_dissertation_status_id()!=null? x.getscience_dissertation_status_id().toString() : "",
//                    x.getemployee_position_id()!=null? x.getemployee_position_id().toString() : "",
//                    x.getauditory_id()!=null? x.getauditory_id().toString() : "",
//                    x.getorder_id()!=null? x.getorder_id().toString() : "",
//                    x.getspec_id()!=null? x.getspec_id().toString() : "",
//
//                    x.getFio_Full(),
//                    x.getOkonchanie_Instituta(),
//                    x.getDis_Sovet_Name(),
//                    x.getDiss_Qualification_Name(),
//                    x.getdegree_detail_short(),
//                    x.getDate_Defense(),
//                    x.getTime_Defense(),
//                    x.getAuditory(),
//                    x.getDate_Admission(),
//                    x.getDate_Vak(),
//                    x.getDate_Minus_10(),
//                    x.getDate_Minus_5(),
//                    x.getDate_Plus_10(),
//                    x.getDate_Plus_15(),
//                    x.getDate_Plus_30(),
//                    x.getFio_Scientific_Adviser(),
//                    x.getOkso_Ds(),
//                    x.getDiss_Spec_Name_Ds(),
//                    x.getTheme(),
//                    x.getDiss_Status(),
//
//                    x.getDate_Doc(),
//                    x.getProtocol_1(),
//                    x.getDate_Protocol_1(),
//                    x.getProtocol_2(),
//                    x.getDate_Protocol_2(),
//                    x.getProtocol_3(),
//                    x.getDate_Protocol_3(),
//                    x.getCertification_Case(),
//                    x.getCertification_Date(),
//                    x.getProtocol_4(),
//                    x.getDate_Protocol_4(),
//
//                    x.getOrder_Number(),
//                    x.getOrder_Date(),
//                    x.getDiplom_Num(),
//                    x.getDiplom_Seria(),
//                    x.getUrl_Vak(),
//                    x.getComment(),
//
//                    x.getDolshnost(),
//                    x.getBirth_Date(),
//                    x.getPhone(),
//                    x.getE_Mail(),
//                    x.getAge(),
//                    x.getSex(),
//                    x.getCountry(),
//                    x.getEmp_Type(),
//                    x.getQr_Code(),
//                    x.getCount_Presents(),
//                    x.getPotocol_3_Count_Voite(),
//                    x.getPotocol_3_Count_Voite_Not(),
//                    x.getPotocol_3_Count_Voite_Forgo(),
//                    x.getPotocol_3_Count_Voite_Bad(),
//                    x.getflag_date_minus_10(),
//                    x.getflag_date_minus_5(),
//                    x.getflag_date_plus_10(),
//                    x.getflag_date_plus_15(),
//                    x.getflag_date_plus_30(),
//                    x.getfio_short(),
//
//                    x.getfapplicant_status(),
//                    x.getf_name_eng(),
//                    x.geti_name_eng(),
//                    x.geto_name_eng(),
//                    x.gettheme_eng(),
//                    x.getkeyword(),
//                    x.getkeyword_eng(),
//                    x.geturl_applicant(),
//                    x.getpart_text(),
//                    x.getcontacts(),
//                    x.getf_name() != null ? x.getf_name() : "",
//                    x.geti_name() != null ? x.geti_name() : "",
//                    x.geto_name() != null ? x.geto_name() : ""
//            )));
//            ArrayList<String> headerName = new ArrayList<>(Arrays.asList("ФИО",
//                    "Срок окончания",
//                    "Диссертационный совет",
//                    "Ученая степень",
//                    "Дата защиты",
//                    "Время защиты",
//                    "Ауд.",
//                    "Дата размещенияна ВАК",
//                    "Дата приказа на защиту",
//                    "-10 дней",
//                    "-5 дней",
//                    "+5 дней",
//                    "+15 дней",
//                    "+30 дней",
//                    "Научный руководитель",
//                    "Шифр научной специальности",
//                    "Нуачная специальность",
//                    "Тема диссертации",
//                    "Статус.",
//                    "Дата сдачи док.",
//                    "№ решения президиума",
//                    "Дата решения президиума",
//                    "№ протокола к защите",
//                    "Дата протокола к защите",
//                    "№ протокола о присвоении степени",
//                    "Дата протокола о присв. степени",
//                    "Дата утв. степени",
//                    "Номер аттестационного дела",
//                    "Дата аттест. дел",
//                    "№ решения АК",
//                    "Дата решения АК",
//                    "№ приказа",
//                    "Дата приказа",
//                    "Серия диплома",
//                    "Номер диплома",
//                    "Ссылка на ВАК",
//                    "Комментарии",
//                    "Должность",
//                    "Дата рождения",
//                    "Телефон",
//                    "Почта",
//                    "Возраст",
//                    "Пол",
//                    "Страна",
//                    "Тип соискателя",
//                    "Qr-код",
//                    "Количество присутствующих",
//                    "Количество за",
//                    "Количество против",
//                    "Количество воздерж.",
//                    "Количество недействит."));
//            //    fields.addAll(Arrays.asList(c.getDeclaredFields()));
//            var fields = new ArrayList<>(Arrays.asList(ScienceDTO.class.getDeclaredFields()));
//            ArrayList<String> fieldsList = new ArrayList<>();
//            fields.forEach(x -> fieldsList.add(x.getName()));
//            //if(scienceList instanceof )
////            System.out.println("scienceList11 " + scienceList);
////            var scienceTable = new ScienceTableDTO(
////                    scienceList,
////                    fieldsList,
////                    headerName,
////                    scienceList.stream().findFirst().get().get_01count_rows()//,
////                   // page_rows,
////                    //page_num
////            );
//
////        var temp= new Gson().toJson(scienceList);
//            //scienceList.forEach(x->x.);
////        System.out.println(temp);
//            // var temp2 = temp
//            //return Collections.singletonList(modelMapper.map(bdFuncResponse, ScienceDto.class));
//            return scienceList;//scienceTable;
//        } else
//            return null;
//    }
//
//    public ArrayList<ScienceSchedulesDTO> getScienceSchedulesJson(long science_dis_id) {
//        ArrayList<ScienceSchedulesDTO> scienceSchedulesList = new ArrayList<>();
//        var bdFuncResponse = scienceRepository.scienceSchedulesFunc(science_dis_id);
//        if (bdFuncResponse.size() > 0) {
//            bdFuncResponse.forEach(x -> scienceSchedulesList.add(new ScienceSchedulesDTO(
//                    x.getscience_dissertation_schedule_id()!=null? x.getscience_dissertation_schedule_id().toString() : "",
//                    x.getscience_dissertation_id()!=null? x.getscience_dissertation_id().toString() : "",
//                    x.getscience_schedule_type_id()!=null? x.getscience_schedule_type_id().toString() : "",
//                    x.getschedule_type_code()!=null? x.getschedule_type_code() : "",
//                    x.getschedule_type_name()!=null? x.getschedule_type_name(): "",
//                    x.getdate_plan()!=null? x.getdate_plan(): "",
//                    x.getdate_fact()!=null? x.getdate_fact(): "")));
//        }
//        return scienceSchedulesList;
//    }

//    public void scienceSchedulesUpdFunc(String data) {
//         scienceRepository.scienceSchedulesUpdFunc(data);
//    }

    public void sciencePersonalInfoUpdFunc(String data) {

    }

//    public ArrayList<ScienceDiplomDTO> getScienceDiplomas(long year_id) {
//        ArrayList<ScienceDiplomDTO> scienceDiplomsList = new ArrayList<>();
//        var bdFuncResponse = scienceRepository.scienceListDiplom(year_id);
//        if (bdFuncResponse.size() > 0) {
//            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ScienceDiplomDTO(
//                    x.getscience_dissertation_id(),
//                    x.getperson_id(),
//                    x.getfio() != null ? x.getfio().toString() : "",
//                    x.getdiss_qualification_name() != null ? x.getdiss_qualification_name() : "",
//                    x.getblank_seria() != null ? x.getblank_seria() : "",
//                    x.getblank_num() != null ? x.getblank_num() : "",
//                    x.getreg_num() != null ? x.getreg_num() : "",
//                    x.getdis_order_num() != null ? x.getdis_order_num() : "",
//                    x.getdis_order_date() != null ? x.getdis_order_date() : "",
//                    x.getdissovet() != null ? x.getdissovet() : "",
//                    x.getdip_order() != null ? x.getdip_order() : "",
//                    x.getdis_sovet_name() != null ? x.getdis_sovet_name() : "",
//                    x.getdate_patent() != null ? x.getdate_patent() : "",
//                    x.getdate_protection() != null ? x.getdate_protection() : "")));
//
//        }
//        return scienceDiplomsList;
//    }



   /* public List<ScienceDto> getScienceAllJsonFromFilters(String filters, int page_rows, int page_num) {
        ArrayList<ScienceDto> scienceList = new ArrayList<>();
        var bdFuncResponse = testRepository.paginationFunc( filters, page_rows, page_num);
        bdFuncResponse.forEach(x -> scienceList.add(new ScienceDto(  x.getFio_Full(),
                x.getOkonchanie_Instituta(),
                                                                                x.getDolshnost(),
                                                                                x.getBirth_Date(),
                                                                                x.getAge(),
                                                                                x.getSex(),
                                                                                x.getCountry(),
                                                                                x.getPhone(),
                                                                                x.getE_Mail(),
                                                                                x.getEmp_Type(),
                                                                                x.getDis_Sovet_Name(),
                                                                                x.getDiss_Qualification_Name(),
                                                                                x.getFio_Scientific_Adviser(),
                                                                                x.getDate_Defense(),
                                                                                x.getTime_Defense(),
                                                                                x.getAuditory(),
                                                                                x.getDate_Doc(),
                                                                                x.getDate_Admission(),
                                                                                x.getDate_Vak(),
                                                                                x.getDate_Minus_10(),
                                                                                x.getDate_Minus_5(),
                                                                                x.getDate_Plus_10(),
                                                                                x.getDate_Plus_15(),
                                                                                x.getDate_Plus_30(),
                                                                                x.getOkso_Ds(),
                                                                                x.getDiss_Spec_Name_Ds(),
                                                                                x.getTheme(),
                                                                                x.getProtocol_1(),
                                                                                x.getDate_Protocol_1(),
                                                                                x.getProtocol_2(),
                                                                                x.getDate_Protocol_2(),
                                                                                x.getProtocol_3(),
                                                                                x.getDate_Protocol_3(),
                                                                                x.getCount_Presents(),
                                                                                x.getPotocol_3_Count_Voite(),
                                                                                x.getPotocol_3_Count_Voite_Not(),
                                                                                x.getPotocol_3_Count_Voite_Forgo(),
                                                                                x.getPotocol_3_Count_Voite_Bad(),
                                                                                x.getProtocol_4(),
                                                                                x.getDate_Protocol_4(),
                                                                                x.getCertification_Case(),
                                                                                x.getCertification_Date(),
                                                                                x.getOrder_Number(),
                                                                                x.getOrder_Date(),
                                                                                x.getDiplom_Num(),
                                                                                x.getDiplom_Seria(),
                                                                                x.getUrl_Vak(),
                                                                                x.getQr_Code(),
                                                                                x.getComment(),
                                                                                x.getDiss_Status()
                )));

//        bdFuncResponse.stream().forEach(x -> employersList.add(new EmployerDTO(x.getFio(), x.getPositions())));
//        return  employersList;
//        System.out.println(modelMapper.map(bdFuncResponse, ScienceDto.class));
//        System.out.println((employersList));

        //return Collections.singletonList(modelMapper.map(bdFuncResponse, ScienceDto.class));
        return  scienceList;
    }*/


}
