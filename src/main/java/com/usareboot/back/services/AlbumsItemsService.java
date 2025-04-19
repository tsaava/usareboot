package com.usareboot.back.services;

import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.operators.AlbumItemOperator;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.VkOperator;
import com.usareboot.back.persistence.usareboot.repository.AlbumsItemsRepository;
import com.usareboot.back.services.vk.VkPostService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumsItemsService {

    private final AlbumsItemsRepository albumsItemsRepository;
    final ModelMapper modelMapper;
    private final AlbumItemOperator albumItemOperator;
    private final VkOperator vkOperator;
    private final VkPostService vkPostService;
    private final ConfigureFeignUrlController configureFeignUrlController;
    private final CommonOperator commonOperator;
    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;

    @Value("${vk.api.groupId}")
    private String groupId;
    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    //    @Async
    public ArrayList<AlbumsItemsDTO> getAlbumsItems(long albumId) {
        var eventId = threadLocal.get();
        log.info("[Сценарий getAlbumsItems][Шаг: Начало][EventID: {}]", eventId);
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
                    x.getItemDescription(),
                    x.getDescription(),
                    x.getItemUrl(),
                    null,
                    null,
                    null,
                    null,
                    x.getDescriptionShort(),
                    x.getAlbumItemWeight(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCount(),
                    null,
                    x.getAlbumItemCost(),
                    x.getAlbumItemRate(),
                    x.getItemColor(),
                    x.getStatuses().getStatusId(),
                    x.getStatuses().getStatusName(),
                    x.getVkPhotoPath(),
                    x.isNoSize()
            )));
        }
//        log.info("[Сценарий getAlbumsItems][Шаг: вывод AlbumsItemsDTO list: {}][EventID: {}]", list, eventId);
        return list;
    }

    public void saveAlbumItem(AlbumsItemsDTO albumsItemsDTO) throws IOException {
        albumItemOperator.saveAlbumItem(albumsItemsDTO);
    }

    public String saveFile(Long albumId, MultipartFile file, AlbumsItemsDTO albumsItemsDTO) throws IOException, ClientException, ApiException {
//        var albumsItemsDTO = vkService.saveFileInVk(albumId, file, adFile1, adFile2, adFile3, dto);
//        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        log.info("Редактирование комментария в вк");
        if (file == null && (albumsItemsDTO.getPhotoUrl() == null || albumsItemsDTO.getPhotoUrl().isEmpty())) {
            log.error("Ошибка загрузки: нет ссылки на фотографию");
            throw new RuntimeException("Ошибка загрузки: нет ссылки на фотографию");
        }

       /* var photoUploadVk = vkOperator.getUrlPhotoInAlbumVk(albumId);
        log.info("Upload photo in vk");
        var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(photoUploadVk, file);
        log.info("vkPhotoList: {}", vkPhotoList);
        log.info("Save photo in vk");
        var photo = vkOperator.savePhotoInVk(vkPhotoList.getPhotos_list(), String.valueOf(albumId), String.valueOf(vkPhotoList.getServer()), vkPhotoList.getHash());
        */
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        log.info("Загружаем фотографии на сервер ВК");
        List<String> photos = vkOperator.uploadPhotoForAlbum(files, Math.toIntExact(albumId));
        var photo = photos.get(0);
        albumsItemsDTO.setItemDescription(albumsItemsDTO.getDescription());

        var allDesc = vkOperator.getAllDesc(albumsItemsDTO);
        albumsItemsDTO.setDescription(allDesc);
        String photoId="";
        if (photo != null && photo.contains("_")) {
            photoId = photo.split("_")[1];
        }

        vkOperator.editPhotoInVk(photoId, allDesc);
        log.info("В ВК фотография успешно загружена и добавлено описание");

        albumsItemsDTO.setVkItemId(Long.parseLong(photoId));
//        albumsItemsDTO.setVkPhotoPath("https://vk.com/photo-" + groupId + "_" + photo);
        albumsItemsDTO.setVkPhotoPath("https://vk.com/photo" + photo);

        Path filePath = Path.of(pathPhoto);
        log.info("Сохранение фото в БД");
        albumsItemsDTO.setPhotoPath(String.valueOf(filePath));
        copyFile(file);
        log.info("Сохранение фото в БД");
        saveAlbumItem(albumsItemsDTO);

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        var result = Map.of(
                "filename", file.getOriginalFilename(),
                "fileUri", fileUri
        );

        log.info("Фото товара успешно загружено, изменено описание и сохранено в бд");
        /*var itemCost = albumsItemsDTO.getAlbumItemCost() * albumsItemsDTO.getAlbumItemRate();
        var description = albumsItemsDTO.getAlbumName() +"\n"+itemCost+"\n"+albumsItemsDTO.getVkPhotoPath();
        VkPostRequestDTO vkPostRequestDTO = VkPostRequestDTO.builder()
                .itemUrl(albumsItemsDTO.getVkPhotoPath())
                .albumName(albumsItemsDTO.getAlbumName())
                .itemCost(itemCost)
                .description(description)
                .build();

        log.info("VkPostRequestDTO: {}",vkPostRequestDTO);

//        vkOperator.postInVk(vkPostRequestDTO,  photos);
        vkPostService.postWithPhotos(vkPostRequestDTO,  photos);*/
        return photoId;
    }

    public void copyFile(MultipartFile file) {
        try {
            // Создаем временный файл для сохранения загруженного файла
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            // Определяем путь назначения для копирования файла
            Path destinationPath = Paths.get(pathPhoto, file.getOriginalFilename());

            // Копируем временный файл в папку назначения
            Files.copy(tempFile, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Удаляем временный файл
            Files.delete(tempFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
