package com.usareboot.back.services;

import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsDTO;
import com.usareboot.back.models.CardsDTO;
import com.usareboot.back.operators.AlbumOperator;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.VkOperator;
import com.usareboot.back.persistence.usareboot.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import com.usareboot.back.persistence.usareboot.repository.AlbumMappingDictionaryRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import com.usareboot.back.persistence.usareboot.repository.CardsRepository;
import com.usareboot.back.persistence.usareboot.repository.DStatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.usareboot.back.models.constant.Constant.ALBUM_DEFAULT_STATUS_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DictionaryService {
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    @Value("${vk.api.groupId}")
    private String groupId;
    @PersistenceContext
    private EntityManager entityManager;

    private final CommonOperator commonOperator;
    @Async
    public void delAlbum(long albumId) {
        var eventId = threadLocal.get();

        /*long albumVkId = albumOperator.getAlbumVkId(albumId);

        log.info("[Сценарий deleteAlbum][Шаг: Удаления альбома в ВК][EventID: {}]", eventId);
        String res = vkOperator.delAlbumInVk(albumVkId);
        log.info("[Сценарий deleteAlbum][Шаг: результат удаления альбома в ВК: {}][EventID: {}]", res, eventId);

        log.info("[Сценарий deleteAlbum][Шаг: Удаления альбома в БД][EventID: {}]", eventId);
        albumOperator.delAlbum(albumId);
        log.info("[Сценарий deleteAlbum][Шаг: Альбом успешно удалился в БД: {}][EventID: {}]", res, eventId);

        log.info("[Сценарий createAlbum][Шаг: Финиш][EventID: {}]", eventId);*/
    }
}
