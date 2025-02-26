package com.usareboot.back.operators;

import com.usareboot.back.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.mapper.BotDbMapper;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.repositories.AlbumMappingDictionaryRepository;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public AlbumsItemsDTO getAlbumItem(VkBotResponseDTO vkBotResponseDTO) {
        log.info("vkBotResponseDTO.getItemUrl(): {}", vkBotResponseDTO.getItemUrl());
        String host = getHost(vkBotResponseDTO);

        AlbumMappingDictionaryEntity albumMapping = albumMappingDictionaryRepository.getAlbumMappingDictionaryEntityByLinkContains(host);
        log.info("albumMapping: {}", albumMapping.getAlbumMappingDictionaryId());

        DStatusesEntity dStatusesEntity = new DStatusesEntity();
        dStatusesEntity.setStatusId(17);
        AlbumsEntity album= albumsRepository.getAlbumsEntityByAlbumMappingDictionaryIdAndStatuses(albumMapping.getAlbumMappingDictionaryId(), dStatusesEntity);
        log.info("album: {}", album.getAlbumId());

        return botDbMapper.mapBotVkToAlbumItem(vkBotResponseDTO, album);
    }

    public void saveItem(AlbumsItemsDTO albumsItemsDTO, VkBotResponseDTO data){
        var albumsItems = albumsItemsRepository.findFirstByVkItemId(albumsItemsDTO.getVkItemId());
        var albumId = albumsItems.getAlbum().getAlbumId();
        var orderId = orderOperator.getOrderId(data.getClientId(), albumId);
        var textOrder = "#Заказ сделан с помощью чат-бота в ВК\n"+
                data.getItemName()+" "+
                data.getItemUrl()+" "+
                data.getItemSize()+" "+
                data.getItemColor()+" "+
                data.getCost()+" "+
                data.getItemCount();
        itemOperator.saveItem(albumsItems, data.getTimestamp(), textOrder, orderId, data);
    }

    private static String getHost(VkBotResponseDTO vkBotResponseDTO) {
        // Регулярное выражение для извлечения хоста
        String regex = "^(https?://)?([^:/\\s]+)(.*)$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(vkBotResponseDTO.getItemUrl());
        String host ="";
        if (matcher.find()) {
            host = matcher.group(2); // Возвращаем хост
        }
        return host;
    }
}
