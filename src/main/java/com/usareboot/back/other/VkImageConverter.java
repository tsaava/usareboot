package com.usareboot.back.other;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.Imaging;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Класс для конвертации изображений в формат, поддерживаемый ВКонтакте API.
 * Поддерживаемые форматы: JPG, PNG, GIF (анимированные GIF не поддерживаются ВК)
 */
@Component
@Slf4j
public class VkImageConverter {

    // Форматы, поддерживаемые ВКонтакте API для загрузки фотографий
    private static final Set<String> SUPPORTED_VK_FORMATS = new HashSet<>(Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif"
    ));

    // MIME-типы WebP
    private static final Set<String> WEBP_MIME_TYPES = new HashSet<>(Arrays.asList(
            "image/webp",
            "image/x-webp"
    ));

   /* static {
        ImageIO.scanForPlugins();
        try {
            ImageIO.setUseCache(false);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("WEBP");
            if (!readers.hasNext()) {
                throw new RuntimeException("WEBP reader not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WEBP support", e);
        }
    }*/
    /**
     * Конвертирует изображение в поддерживаемый VK API формат
     */
    public MultipartFile convertToSupportedFormat(MultipartFile file) throws IOException {
        // Проверка типа файла
        String contentType = file.getContentType();
        log.debug("contentType: {}", contentType);
        if (contentType == null) {
            throw new IllegalArgumentException("Не удалось определить тип файла");
        }

        // Если формат уже поддерживается - возвращаем как есть
        if (SUPPORTED_VK_FORMATS.contains(contentType.toLowerCase())) {
            log.debug("Формат уже поддерживается - возвращаем как есть");
            return file;
        }

        // Чтение изображения с учетом WebP
        BufferedImage image = readImage(file);

        // Конвертация в JPG (по умолчанию)
        byte[] bytes = convertToJpeg(image);
        return new MockMultipartFile(
                "file",         // Имя файла
                "file",         // Оригинальное имя файла
                "image/jpeg",
                bytes       // Данные файла
        );
    }

    /**
     * Чтение изображения с поддержкой WebP
     */
    private BufferedImage readImage(MultipartFile file) throws IOException {
        try {
            // Используем Apache Commons Imaging для лучшей поддержки форматов
            return Imaging.getBufferedImage(file.getBytes());
        } catch (Exception e) {
            // Fallback к стандартному ImageIO с регистрацией WebP плагина
            ImageIO.scanForPlugins();
            return ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        }
    }

    /**
     * Конвертация в JPG формат
     */
    private byte[] convertToJpeg(BufferedImage image) throws IOException {
        BufferedImage convertedImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // Отрисовка с белым фоном (для прозрачных изображений)
        convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(convertedImage, "jpg", outputStream);
        log.debug("файл конвертирован в jpg");
        return outputStream.toByteArray();
    }

    /**
     * Проверяет, требуется ли конвертация для VK API
     */
    public boolean needsConversion(MultipartFile file) {
        if (file.getContentType() == null) return true;

        String mimeType = file.getContentType().toLowerCase();
        return !SUPPORTED_VK_FORMATS.contains(mimeType);
    }

    /**
     * Определяет, является ли файл WebP изображением
     */
    public boolean isWebpImage(MultipartFile file) {
        if (file.getContentType() == null) return false;
        return WEBP_MIME_TYPES.contains(file.getContentType().toLowerCase());
    }

    /**
     * Реализация MultipartFile для обертки byte[]
     */
    private static class ByteArrayMultipartFile implements MultipartFile {
        private final byte[] content;
        private final String name;
        private final String originalFilename;
        private final String contentType;

        public ByteArrayMultipartFile(byte[] content, String name, String originalFilename, String contentType) {
            this.content = content;
            this.name = name;
            this.originalFilename = originalFilename != null ?
                    originalFilename.replaceFirst("\\.[^.]+$", ".jpg") : null;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            new java.io.FileOutputStream(dest).write(content);
        }
    }
}