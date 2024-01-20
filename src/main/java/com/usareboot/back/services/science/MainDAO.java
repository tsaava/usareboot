package com.usareboot.back.services.science;

import com.usareboot.back.dto.ImportDTO;
import com.usareboot.back.dto.ItemListDTO;
import com.usareboot.back.entities.ImportItemListEntity;
import com.usareboot.back.repositories.ImportListRepository;
import com.usareboot.back.repositories.ImportRepository;
import com.usareboot.back.repositories.MainRepository;
import com.usareboot.back.repositories.science.ScienceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainDAO {
    @Autowired
//    private ImportRepository importRepository;
    private ImportListRepository importListRepository;

    public ArrayList<ImportDTO> getListImport(String listAlbom) {
        ArrayList<ImportDTO> scienceDiplomsList = new ArrayList<>();
        var bdFuncResponse = importListRepository.importListProcedure(listAlbom);
        if (bdFuncResponse.size() > 0) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ImportDTO(
//                   x.getimport_item_list_id(),
                    x.getclient(),
                    x.getclient_id(),
                    x.getvikup(),
                    x.getrazdacha(),
//                    x.getpack(),
//                    x.getnote(),
//                    x.getsender(),
//                    x.getdate(),
//                    x.getaddress(),
//                    x.getfio(),
//                    x.getphone(),
//                    x.getemail(),
//                    x.getstatus(),
//                    x.getnum_order(),
//                    x.getisdownload(),
//                    x.getitem_color(),
//                    x.getitem_size(),
//                    x.getitem_weight(),
                    x.getitem_name(),
                    x.getitem_count(),
                    x.getsp_help_id(),
//                    x.getcomment(),
                    x.getitem_cost()
                    )));

        }
        return scienceDiplomsList;
    }

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void getImportList(String data , String albomName){
//        System.out.println(albomName.replace("\"",""));
        var isHave=importListRepository.getImportItemListEntitiesByVikup(albomName);
        System.out.println("isHave: "+isHave);
        if(isHave.isEmpty()) {
            System.out.println("выполняется процедура импорта");
            StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("vpImportDataInList");
            spq.setParameter("data", data);
            spq.setParameter("albom_name", albomName);
            spq.execute();
        }
    }


    public ArrayList<ItemListDTO> getItemListDao() {
        ArrayList<ItemListDTO> scienceDiplomsList = new ArrayList<>();
        var bdFuncResponse = importListRepository.itemListProcedure();
        if (bdFuncResponse.size() > 0) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ItemListDTO(
                    x.getitem_id(),
                    x.getclient_id(),
                    x.getstatus_id(),
                    x.getalbom_id(),
                    x.getvk_id(),
                    x.getorder_id(),
                    x.getfi(),
                    x.getfio(),
                    x.getitem_name(),
                    x.getalbom_name(),
                    x.getitem_size(),
                    x.getitem_count(),
                    x.getstatus_name(),
                    x.getitem_weight(),
                    x.getdate_delivery(),
                    x.getsp_help_id(),
                    x.getrazdacha()
                    )));
        }
        return scienceDiplomsList;
    }
}
