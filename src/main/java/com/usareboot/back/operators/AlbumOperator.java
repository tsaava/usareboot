package com.usareboot.back.operators;

import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.persistence.usareboot.entities.*;
import com.usareboot.back.persistence.usareboot.repository.*;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.usareboot.back.models.constant.Constant.ALBUM_CLOSE_STATUS_ID;
import static com.usareboot.back.models.constant.Constant.ITEM_IN_REDEEMED_STATUS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumOperator {
    private final AlbumsRepository albumsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final ItemsRepository itemsRepository;
    private final DStatusRepository statusRepository;
    private final CardsRepository cardsRepository;
    private final VkOperator vkOperator;

    public long getAlbumVkId(long albumId) {
        AlbumsEntity albumsEntityByAlbum = albumsRepository.findAlbumsEntityByAlbumId(albumId);
        return albumsEntityByAlbum.getAlbumVkId();
    }

    public void delAlbum(long albumId) {
        albumsRepository.deleteById(albumId);
    }

    @Transactional
    public boolean albumsUpd(AlbumRowRequestDTO albumRowRequestDTO) {
        boolean isCommentsDisabled = false;
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
//        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        temp.setCreateDate(LocalDateTime.now());
        if (albumRowRequestDTO.getAlbumStatus() != null && !(albumRowRequestDTO.getAlbumStatus().isEmpty())) {
            log.debug("albumRowRequestDTO.getAlbumStatus(): {}", albumRowRequestDTO.getAlbumStatus());
            DStatusesEntity dStatusesEntityByStatusName = statusRepository.findDStatusesEntityByStatusName(albumRowRequestDTO.getAlbumStatus());
            temp.setStatuses(dStatusesEntityByStatusName);
            log.debug("dStatusesEntityByStatusName: {}", dStatusesEntityByStatusName);
            if (dStatusesEntityByStatusName.getStatusId() == ALBUM_CLOSE_STATUS_ID)
                isCommentsDisabled = true;
        }
        if (albumRowRequestDTO.getCard() != null && !(albumRowRequestDTO.getCard().isEmpty())) {

            CardsEntity card = cardsRepository.findCardsEntityByCardName(albumRowRequestDTO.getCard());
            log.debug("card:{}", card);
            temp.setCards(card);
        }
        log.debug("albumRowRequestDTO: {}", albumRowRequestDTO);
        albumsRepository.save(temp);
        return isCommentsDisabled;
    }

    public void commentsDisabled(AlbumRowRequestDTO albumRowRequestDTO) throws ClientException, IOException, ApiException {
        try {
            AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
            log.debug("Закрытие альбома {} для редактирования", temp.getAlbumName());
            vkOperator.commentsDisabledInVkAlbum(albumRowRequestDTO.getAlbumVkId());
            log.debug("Альбом {} закрыт для редактирования", temp.getAlbumName());
        } catch (Exception e) {
            log.error("Не удалось закрыть комментарии в закрытый альбом");
            throw e;
        }
    }

    public void insertCountBuyOutItems(Long albumId) {
        try {
            List<AlbumsItemsEntity> listAlbumItems = albumsItemsRepository.findAllByAlbumAlbumId(albumId).orElse(new ArrayList<>());

            log.debug("listAlbumItems: {}", listAlbumItems);

            if (listAlbumItems.isEmpty())
                return;
            List<Long> albumItemIds = new ArrayList<>();
            for (var albumItem:listAlbumItems) {
                albumItemIds.add(albumItem.getAlbumItemId());
            }
            List<ItemsEntity> itemsBuyOut = itemsRepository.findByAlbomItemIdInAndItemStatus(albumItemIds, ITEM_IN_REDEEMED_STATUS_ID).orElse(new ArrayList<>());
            log.debug("itemsBuyOut: {}", itemsBuyOut);

            var album = albumsRepository.findAlbumsEntityByAlbumId(albumId);
            album.setCountOrder(itemsBuyOut.size());
            albumsRepository.save(album);

            log.debug("Сохранение кол-во выкупленных товаров у альбома {}:{}", album.getAlbumName(), album.getAlbumId());
        } catch (Exception e) {
            log.error("Не удалось закрыть комментарии в закрытый альбом");
            throw e;
        }
    }

    public String uploadPhotoStop(AlbumRowRequestDTO albumRowRequestDTO) throws ClientException, IOException, ApiException {
        try {
            var file = loadFile();
            AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
            log.debug("Закрытие альбома {} для редактирования", temp.getAlbumName());
            String res = vkOperator.uploadToAlbumStopFile((int) albumRowRequestDTO.getAlbumVkId(), file);
            log.debug("result: {}", res);
            log.debug("Альбом {} закрыт для редактирования", temp.getAlbumName());
            return res;
        } catch (Exception e) {
            log.error("Не удалось загрузить фото стоп в закрытый альбом");
            throw e;
        }
    }

    private File loadFile() throws IOException {
        // 1. Пробуем загрузить из внешней папки
        Path externalPath = Paths.get("vk_img", "stop.jpg");
        if (externalPath.toFile().exists()) {
            return externalPath.toFile();
        }

        // 2. Если не найден, пробуем из ресурсов (например, fallback)
        ClassPathResource resource = new ClassPathResource("vk_img/stop.jpg");
        try {
            return resource.getFile(); // сработает, если файл на диске (не в JAR)
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден ни во внешней папке, ни в ресурсах");
        }
    }

}
