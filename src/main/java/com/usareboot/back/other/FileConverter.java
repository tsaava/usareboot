package com.usareboot.back.other;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileConverter {

    /**
     * Конвертирует MultipartFile в File и автоматически удаляет временный файл после использования
     *
     * @param multipartFiles исходные файлы
     * @throws IOException если произошла ошибка при работе с файлом
     */
//    public static List<File> withTempFile(List<MultipartFile> multipartFiles) throws IOException {
//        List<File> files = new ArrayList<>();
//        for (MultipartFile multipartFile : multipartFiles) {
//
//            if (multipartFile == null || multipartFile.isEmpty()) {
//                throw new IllegalArgumentException("MultipartFile не может быть null или пустым");
//            }
//
//            File tempFile = null;
//            try {
//                // Создаем временный файл
//                tempFile = File.createTempFile("upload-",
//                        "-" + sanitizeFilename(multipartFile.getOriginalFilename()));
//
//                // Копируем содержимое MultipartFile в файл
//                multipartFile.transferTo(tempFile);
//                files.add(tempFile);
//                // Выполняем операцию с файлом
//            } finally {
//                // Удаляем временный файл после использования
//                if (tempFile != null && tempFile.exists()) {
//                    Files.deleteIfExists(tempFile.toPath());
//                }
//            }
//        }
//
//    }

    /**
     * Очищает имя файла от потенциально опасных символов
     */
    private static String sanitizeFilename(String filename) {
        if (filename == null) return "";
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

}
