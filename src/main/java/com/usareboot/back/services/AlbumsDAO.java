package com.usareboot.back.services;

import com.usareboot.back.dto.AlbumsDTO;
import com.usareboot.back.repositories.AlbumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AlbumsDAO {

    private  AlbumsRepository albumsRepository;

    @Autowired
    public AlbumsDAO(AlbumsRepository albumsRepository){
        this.albumsRepository=albumsRepository;
    }

    public ArrayList<AlbumsDTO> getListAlbums(Date albumDate) {
        ArrayList<AlbumsDTO> albumsDTOArrayList = new ArrayList<>();
        var bdFuncResponse = albumsRepository.getAllByAlbumDate(albumDate);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> albumsDTOArrayList.add(new AlbumsDTO(
                    x.getAlbumId(),
                    x.getAlbumName(),
                    x.getAlbumDate(),
                    x.getCourseExchange(),
                    x.getAlbumDatePlane(),
                    x.getCountOrder(),
                    x.getCountry(),
                    x.getShopName(),
                    x.getShopUrl(),
                    x.getAlbumVkUrl(),
                    x.getPackageId(),
                    x.getCourseBank(),
                    x.getCourseAlbum(),
                    x.getBankName(),
                    x.getTrackNumber(),
                    x.getWarehouse(),
                    x.getAlbumDateStop(),
                    x.getAlbumVkId(),
                    x.getDateStop(),
                    x.getAlbumStatus(),
                    x.getCourseBankId()
            )));

        }
        System.out.println(albumsDTOArrayList);
        return albumsDTOArrayList;
    }
}
