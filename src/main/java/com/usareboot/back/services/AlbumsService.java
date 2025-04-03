package com.usareboot.back.services;

import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsDTO;
import com.usareboot.back.models.CardsDTO;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.operators.AlbumOperator;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.VkOperator;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.CardsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumsService {
    private final AlbumsRepository albumsRepository;
    private final CardsRepository cardsRepository;
    private final DStatusRepository statusRepository;
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    @Value("${vk.api.groupId}")
    private String groupId;
    @PersistenceContext
    private EntityManager entityManager;

    private final CommonOperator commonOperator;
    private final VkOperator vkOperator;
    private final AlbumOperator albumOperator;


    /**
     * Список альбомов
     *
     * @return
     */
    public ArrayList<AlbumsDTO> getListAlbums() {
        var eventId = threadLocal.get();
        log.info("[Сценарий getListAlbums][Шаг: Начало][EventID: {}]", eventId);
        ArrayList<AlbumsDTO> albumsDTOArrayList = new ArrayList<>();
        long millis = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(millis);
        var datePolGoda = new java.sql.Date(sqlDate.getTime() - (190L * 24 * 60 * 60 * 1000));
        var bdFuncResponse = albumsRepository.getAlbumsEntitiesByAlbumDateAfterOrderByAlbumDate(datePolGoda);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> albumsDTOArrayList.add(new AlbumsDTO(
                    x.getAlbumId(),
                    x.getAlbumName(),
                    x.getAlbumDate().toString(),
                    x.getAlbumDatePlane() != null ? x.getAlbumDatePlane().toString() : null,
                    x.getCountOrder(),
                    x.getCountry(),
                    x.getAlbumDesc(),
                    x.getShopUrl(),
                    x.getAlbumVkUrl(),
                    x.getPackageId(),
                    x.getCourseBank(),
                    x.getCourseAlbum(),
                    x.getBankName(),
                    x.getTrackNumber(),
                    x.getWarehouse(),
                    x.getAlbumDateStop() != null ? x.getAlbumDateStop().toString() : null,
                    x.getAlbumVkId(),
                    x.getCourseBankId(),
                    x.getStatuses() != null ? x.getStatuses().getStatusId() : null,
                    x.getStatuses() != null ? x.getStatuses().getStatusName() : null,
                    x.getCards() != null ? x.getCards().getCardId() : null,
                    x.getCards() != null ? x.getCards().getCardName() : null
            )));
        }
        log.info("[Сценарий getListAlbums][Шаг: финиш][EventID: {}]", eventId);
        return albumsDTOArrayList;
    }

    @Async
    @Transactional
    public void albumsAdd(AlbumsEntity albumsEntity, Integer id) {
        DStatusesEntity dst = new DStatusesEntity();
        dst.setStatusId(17);
        albumsEntity.setStatuses(dst);
        if (id != null) {
            albumsEntity.setAlbumVkUrl("https://vk.com/album-" + groupId + "_" + id);
            albumsEntity.setAlbumVkId(id);
        }
        this.entityManager.persist(albumsEntity);
    }

    @Async
    public void delAlbum(long albumId) {
        var eventId = threadLocal.get();

        long albumVkId = albumOperator.getAlbumVkId(albumId);

        log.info("[Сценарий deleteAlbum][Шаг: Удаления альбома в ВК][EventID: {}]", eventId);
        String res = vkOperator.delAlbumInVk(albumVkId);
        log.info("[Сценарий deleteAlbum][Шаг: результат удаления альбома в ВК: {}][EventID: {}]", res, eventId);

        log.info("[Сценарий deleteAlbum][Шаг: Удаления альбома в БД][EventID: {}]", eventId);
        albumOperator.delAlbum(albumId);
        log.info("[Сценарий deleteAlbum][Шаг: Альбом успешно удалился в БД: {}][EventID: {}]", res, eventId);

        log.info("[Сценарий createAlbum][Шаг: Финиш][EventID: {}]", eventId);
    }

    @Async
    public void createPost(String message) {
        /*var eventId = threadLocal.get();

        log.info("[Сценарий createPost][Шаг: Добавление поста на стену группы в ВК][EventID: {}]", eventId);
//        String message = "message";
        String res = vkOperator.postInVk(message);
log.info("res: {}", res);
        log.info("[Сценарий createAlbum][Шаг: Финиш][EventID: {}]", eventId);*/
    }

    @Transactional
    public void albumsUpd(AlbumRowRequestDTO albumRowRequestDTO, long id) {
//        AlbumsEntity album = modelMapper.map(albumRowRequestDTO, AlbumsEntity.class);
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        if (albumRowRequestDTO.getAlbumStatus() != null && !(albumRowRequestDTO.getAlbumStatus().isEmpty()))
            temp.setStatuses(statusRepository.findDStatusesEntityByStatusName(albumRowRequestDTO.getAlbumStatus()));
        if (albumRowRequestDTO.getCard() != null && !(albumRowRequestDTO.getCard().isEmpty()))
            temp.setCards(cardsRepository.findCardsEntityByCardName(albumRowRequestDTO.getCard()));

        albumsRepository.save(temp);
//        DStatusesEntity dst=new DStatusesEntity();
//        dst.setStatusId(17);
//        this.entityManager.persist(albumsEntity);
    }

    /**
     * Список наименования карт для оплаты выкупов
     *
     * @return
     */
    public ArrayList<CardsDTO> getAlbumCards() {
        ArrayList<CardsDTO> list = new ArrayList<>();
        var bdFuncResponse = cardsRepository.findAll();
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> list.add(new CardsDTO(
                    x.getCardId(),
                    x.getCardName(),
                    x.getPercent()
            )));
        }
//        System.out.println(list);
        return list;
    }

    public void convertToEntity(AlbumRowRequestDTO albumRowRequestDTO) throws ParseException {
       /* AlbumsEntity album = modelMapper.map(albumRowRequestDTO, AlbumsEntity.class);
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        albumsRepository.save(temp);*/
//        temp.getCourseAlbum(albumRowRequestDTO.getCourseAlbum());
//        post.setSubmissionDate(albumRowRequestDTO.getSubmissionDateConverted(
//                userService.getCurrentUser().getPreference().getTimezone()));

//        if (albumRowRequestDTO.getAlbumId() != null) {
//            AlbumsEntity oldPost = postService.getPostById(albumRowRequestDTO.getId());
//            post.setRedditID(oldPost.getRedditID());
//            post.setSent(oldPost.isSent());
//        }
//        return album;
    }


}
