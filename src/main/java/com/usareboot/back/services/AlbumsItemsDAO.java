package com.usareboot.back.services;

import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AlbumsItemsDAO {

    @Autowired
    private AlbumsItemsRepository albumsItemsRepository;

    @Autowired
    private DStatusRepository dStatusRepository;

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    ModelMapper modelMapper;


    public ArrayList<AlbumsItemsDTO> getAlbumsItems(long albumId) {

//        ArrayList<AlbumsItemsEntity> albumsItems = albumsItemsRepository.getAlbumsItemsEntitiesByAlbumId(albumId);
//        System.out.println(albumsItems);
//        return modelMapper.map(albumsItems, AlbumsItemsDTO.class);

        ArrayList<AlbumsItemsDTO> list = new ArrayList<>();
        var bdFuncResponse = albumsItemsRepository.getAlbumsItemsEntitiesByAlbum_AlbumId(albumId);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> list.add(new AlbumsItemsDTO(
                    x.getAlbumItemId(),
                    x.getAlbum().getAlbumId(),
                    x.getAlbum().getAlbumName(),
                    x.getVkItemId(),
                    x.getTgItemId(),
                    x.getPhotoPath(),
                    x.getDescription(),
                    x.getItemUrl(),
                    x.getDescriptionShort(),
                    x.getAlbumItemWeight(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCount(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCost(),
                    x.getAlbumItemRate(),
                    x.getStatuses().getStatusId(),
                    x.getStatuses().getStatusName(),
                    null,
                    x.getVkPhotoPath()
            )));
        }
        System.out.println(list);
        return list;
    }

    public void saveAlbumItem(AlbumsItemsDTO albumsItemsDTO) {
//        AlbumsItemsEntity post = modelMapper.map(albumsItemsDTO, AlbumsItemsEntity.class);
//        albumsItemsRepository.save(post);
        AlbumsItemsEntity albumsItemsEntity = new AlbumsItemsEntity();
        albumsItemsEntity.setAlbumItemStatus(dStatusRepository.findDStatusesEntityByStatusId(albumsItemsDTO.getAlbumItemStatus()));
        albumsItemsEntity.setAlbumItemName(albumsItemsDTO.getAlbumItemName());
        albumsItemsEntity.setAlbumItemCost(albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setAlbumItemRate(albumsItemsDTO.getAlbumItemRate());
        albumsItemsEntity.setItemUrl(albumsItemsDTO.getItemUrl());
        albumsItemsEntity.setVkItemId(albumsItemsDTO.getVkItemId());
        albumsItemsEntity.setAlbumId(albumsRepository.findAlbumsEntityByAlbumId(albumsItemsDTO.getAlbumId()));
        LocalDate localDate = LocalDate.now();
        albumsItemsEntity.setDateCreate(Date.valueOf(localDate));
        albumsItemsEntity.setPhotoPath(albumsItemsDTO.getPhotoPath());
        albumsItemsEntity.setVkPhotoPath(albumsItemsDTO.getVkPhotoPath());
        albumsItemsEntity.setDescription(albumsItemsDTO.getDescription());
        albumsItemsRepository.save(albumsItemsEntity);
    }
}
