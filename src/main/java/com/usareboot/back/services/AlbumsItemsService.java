package com.usareboot.back.services;

import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import com.usareboot.back.services.vk.VkService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@Service
@Slf4j
public class AlbumsItemsService {

    @Autowired
    private AlbumsItemsRepository albumsItemsRepository;

    @Autowired
    private DStatusRepository dStatusRepository;

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    private VkService vkService;

    @Autowired
    ModelMapper modelMapper;
    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;
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
                    x.getDescription(),
                    x.getItemUrl(),
                    null,
                    x.getDescriptionShort(),
                    x.getAlbumItemWeight(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCount(),
                    x.getAlbumItemName(),
                    x.getAlbumItemCost(),
                    x.getAlbumItemRate(),
                    x.getItemColor(),
                    x.getStatuses().getStatusId(),
                    x.getStatuses().getStatusName(),
                    x.getVkPhotoPath(),
                    x.isNoSize()
            )));
        }
        log.info("[Сценарий getAlbumsItems][Шаг: вывод AlbumsItemsDTO list: {}][EventID: {}]", list, eventId);
        return list;
    }

    public void saveAlbumItem(AlbumsItemsDTO albumsItemsDTO) throws IOException {
        AlbumsItemsEntity albumsItemsEntity = new AlbumsItemsEntity();
        albumsItemsEntity.setAlbumItemStatus(dStatusRepository.findDStatusesEntityByStatusId(albumsItemsDTO.getAlbumItemStatus()));
        albumsItemsEntity.setAlbumItemName(albumsItemsDTO.getAlbumItemName());
        albumsItemsEntity.setAlbumItemCost(albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setAlbumItemRate(albumsItemsDTO.getAlbumItemRate());
        albumsItemsEntity.setItemColor(albumsItemsDTO.getAlbumItemColor());
        albumsItemsEntity.setCost(albumsItemsDTO.getAlbumItemRate() * albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setItemUrl(albumsItemsDTO.getItemUrl());
        albumsItemsEntity.setVkItemId(albumsItemsDTO.getVkItemId());
        albumsItemsEntity.setAlbumId(albumsRepository.findAlbumsEntityByAlbumId(albumsItemsDTO.getAlbumId()));
        LocalDate localDate = LocalDate.now();
        albumsItemsEntity.setDateCreate(Date.valueOf(localDate));
        albumsItemsEntity.setVkPhotoPath(albumsItemsDTO.getVkPhotoPath());
        albumsItemsEntity.setDescription(albumsItemsDTO.getDescription());
        albumsItemsEntity.setNoSize(albumsItemsDTO.isNoSize());
        try {
            var photoUrl = vkService.getCommentPhotoVk(albumsItemsDTO.getVkItemId());
            albumsItemsEntity.setPhotoPath(photoUrl);
        } catch (Exception e) {
            log.error("Не удалось получить ссылку на фото в Вк", e);
        }

        albumsItemsRepository.save(albumsItemsEntity);
    }

    public ResponseEntity<Map<String, String>> saveFile(MultipartFile file, AlbumsItemsDTO albumsItemsDTO) throws IOException {
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

        return ok().body(result);

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
