package com.usareboot.back.services;

import com.usareboot.back.dto.AlbumsItemsDTO;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlbumsItemsDAO {

    @Autowired
    private AlbumsItemsRepository albumsItemsRepository;

    @Autowired
    ModelMapper modelMapper;


    public ArrayList<AlbumsItemsDTO> getAlbumsItems(long albumId) {

//        ArrayList<AlbumsItemsEntity> albumsItems = albumsItemsRepository.getAlbumsItemsEntitiesByAlbumId(albumId);
//        System.out.println(albumsItems);
//        return modelMapper.map(albumsItems, AlbumsItemsDTO.class);

        ArrayList<AlbumsItemsDTO> list = new ArrayList<>();
        var bdFuncResponse = albumsItemsRepository.getAlbumsItemsEntitiesByAlbumId(albumId);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> list.add(new AlbumsItemsDTO(
                    x.getAlbumItemId(),
                    x.getAlbum().getAlbumId(),
                    x.getAlbum().getAlbumName(),
                    x.getVkItemId(),
                    x.getTgItemId(),
                    x.getPhotoPath(),
                    x.getDescription(),
                    x.getDescriptionShort(),
                    x.getAlbumItemWeight(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCount(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCost(),
                    x.getAlbumItemRate(),
                    x.getStatuses().getStatusId(),
                    x.getStatuses().getStatusName()
            )));
        }
        System.out.println(list);
        return list;
    }
}
