package com.usareboot.back.operators;

import com.usareboot.back.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.mapper.BotDbMapper;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.repositories.AlbumMappingDictionaryRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotVkOperator {
    private final BotDbMapper botDbMapper;
    private final AlbumMappingDictionaryRepository albumMappingDictionaryRepository;
    private final AlbumsRepository albumsRepository;


    public AlbumsItemsDTO getAlbumItem(VkBotResponseDTO vkBotResponseDTO) {
        log.info("vkBotResponseDTO.getItemUrl(): {}", vkBotResponseDTO.getItemUrl());
        // Регулярное выражение для извлечения хоста
        String regex = "^(https?://)?([^:/\\s]+)(.*)$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(vkBotResponseDTO.getItemUrl());
        String host ="";
        if (matcher.find()) {
            host = matcher.group(2); // Возвращаем хост
        }
        /*URI uri = new URI(vkBotResponseDTO.getItemUrl());
        log.info("uri: {}", uri);

        String host = uri.getHost();
        log.info("host: {}", host);*/

        AlbumMappingDictionaryEntity albumMapping = albumMappingDictionaryRepository.getAlbumMappingDictionaryEntityByLinkContains(host);
        log.info("albumMapping: {}", albumMapping.getAlbumMappingDictionaryId());

        DStatusesEntity dStatusesEntity = new DStatusesEntity();
        dStatusesEntity.setStatusId(17);
        AlbumsEntity album= albumsRepository.getAlbumsEntityByAlbumMappingDictionaryIdAndStatuses(albumMapping.getAlbumMappingDictionaryId(), dStatusesEntity);
        log.info("album: {}", album.getAlbumId());

        return botDbMapper.mapBotVkToAlbumItem(vkBotResponseDTO, album);
    }
}
