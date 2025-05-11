package com.usareboot.back.operators;

import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.persistence.usareboot.entities.CardsEntity;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import com.usareboot.back.persistence.usareboot.repository.CardsRepository;
import com.usareboot.back.persistence.usareboot.repository.DStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumOperator {
    private final AlbumsRepository albumsRepository;
    private final DStatusRepository statusRepository;
    private final CardsRepository cardsRepository;

    public long getAlbumVkId(long albumId) {
        AlbumsEntity albumsEntityByAlbum = albumsRepository.findAlbumsEntityByAlbumId(albumId);
        return albumsEntityByAlbum.getAlbumVkId();
    }

    public void delAlbum(long albumId) {
        albumsRepository.deleteById(albumId);
    }

    @Transactional
    public void albumsUpd(AlbumRowRequestDTO albumRowRequestDTO, long id) {
//        AlbumsEntity album = modelMapper.map(albumRowRequestDTO, AlbumsEntity.class);
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
//        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        temp.setCreateDate(LocalDateTime.now());
        if (albumRowRequestDTO.getAlbumStatus() != null && !(albumRowRequestDTO.getAlbumStatus().isEmpty()))
            temp.setStatuses(statusRepository.findDStatusesEntityByStatusName(albumRowRequestDTO.getAlbumStatus()));

        if (albumRowRequestDTO.getCard() != null && !(albumRowRequestDTO.getCard().isEmpty())) {

            CardsEntity card = cardsRepository.findCardsEntityByCardName(albumRowRequestDTO.getCard());
            log.debug("card:{}",card);
            temp.setCards(card);
        }
        log.debug("albumRowRequestDTO: {}", albumRowRequestDTO);
        albumsRepository.save(temp);
//        DStatusesEntity dst=new DStatusesEntity();
//        dst.setStatusId(17);
//        this.entityManager.persist(albumsEntity);
    }
}
