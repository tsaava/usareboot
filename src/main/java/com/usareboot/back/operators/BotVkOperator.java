package com.usareboot.back.operators;

import com.usareboot.back.persistence.usareboot.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import com.usareboot.back.mapper.BotDbMapper;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.persistence.usareboot.repository.AlbumMappingDictionaryRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsItemsRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotVkOperator {
    private final BotDbMapper botDbMapper;
    private final AlbumMappingDictionaryRepository albumMappingDictionaryRepository;
    private final AlbumsRepository albumsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final ItemOperator itemOperator;
    private final OrderOperator orderOperator;
    private final VkApiClient vk;
    private final GroupActor actor;
    private final String DEFAULT_ALBUM_URL = "https://default";
    private final long DEFAULT_ALBUM_MAPPING_ID = 2L;

    public AlbumsItemsDTO getAlbumItem(VkBotResponseDTO vkBotResponseDTO) {
        log.info("vkBotResponseDTO.getItemUrl(): {}", vkBotResponseDTO.getItemUrl());
        String host = getHost(vkBotResponseDTO.getItemUrl());
        AlbumsEntity album = getAlbumsEntity(host);
        return botDbMapper.mapBotVkToAlbumItem(vkBotResponseDTO, album);
    }

    public AlbumsEntity getAlbumsEntity(String host) {
        AlbumMappingDictionaryEntity albumMapping = albumMappingDictionaryRepository.getAlbumMappingDictionaryEntityByLinkContains(host).orElse(null);

        if (albumMapping == null) {
            albumMapping = albumMappingDictionaryRepository.getAlbumMappingDictionaryEntityByLinkContains(DEFAULT_ALBUM_URL).orElse(null);
        }
        long albumMappingId = Optional.ofNullable(albumMapping).map(AlbumMappingDictionaryEntity::getAlbumMappingDictionaryId).orElse(DEFAULT_ALBUM_MAPPING_ID);
        log.info("albumMapping: {}", albumMappingId);

        DStatusesEntity dStatusesEntity = new DStatusesEntity();
        dStatusesEntity.setStatusId(17);
        AlbumsEntity album = albumsRepository.getAlbumsEntityByAlbumMappingDictionaryIdAndStatuses(albumMappingId, dStatusesEntity).orElse(null);
        if (album == null) {
            album = albumsRepository.getAlbumsEntityByAlbumMappingDictionaryIdAndStatuses(DEFAULT_ALBUM_MAPPING_ID, dStatusesEntity).orElse(null);
        }
        Optional<Long> albumId = Optional.ofNullable(album).map(AlbumsEntity::getAlbumId);
        log.info("album: {}", albumId);
        return album;
    }

    public void saveItem(AlbumsItemsDTO albumsItemsDTO, VkBotResponseDTO data) {
        var albumsItems = albumsItemsRepository.findFirstByVkItemId(albumsItemsDTO.getVkItemId());
        var albumId = albumsItems.getAlbum().getAlbumId();
        var orderId = orderOperator.getOrderId(data.getClientId(), albumId);
        var textOrder = "#Заказ сделан с помощью чат-бота в ВК\n" +
                data.getItemName() + " " +
                data.getItemUrl() + " " +
                data.getItemSize() + " " +
                data.getItemColor() + " " +
                data.getItemCost() + " " +
                data.getItemCount();
        itemOperator.saveItem(albumsItems, data.getTimestamp(), textOrder, orderId, data);
    }

    public String getHost(String itemUrl) {
        try {
            // Регулярное выражение для извлечения хоста
            String regex = "^(https?://)?([^:/\\s]+)(.*)$";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher matcher = pattern.matcher(itemUrl);
            String host = "";
            if (matcher.find()) {
                host = matcher.group(2); // Возвращаем хост
            }
            log.info("host: {}", host);
            return host;
        } catch (Exception e) {
            log.error("Не удалось распарсить хост: {}", e.getMessage());
            return DEFAULT_ALBUM_URL;
        }
    }

    public void sendMessageWithKeyboard(int userId, Keyboard keyboard, String text) {
        try {
            log.info("sendMessageWithKeyboard: {}", keyboard);
            vk.messages()
                    .send(actor)
                    .userId(userId)
                    .message(text)
                    .keyboard(keyboard)
                    .randomId(new Random().nextInt(10000))
                    .execute();
        } catch (ApiException | ClientException e) {
            log.error(e.toString());
        }
    }
}
