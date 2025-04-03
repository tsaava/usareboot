package com.usareboot.back.other;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ImageCompressor {

    // Оптимальные настройки для VK
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;
    private static final float QUALITY = 0.85f;
    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024; // 5MB

    public static byte[] compressImage(MultipartFile file) throws IOException {
        // Читаем оригинальное изображение
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

        // Определяем ориентацию изображения
        boolean isPortrait = originalImage.getHeight() > originalImage.getWidth();

        // Сжимаем с сохранением пропорций
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(MAX_WIDTH, MAX_HEIGHT)
                .outputFormat("jpg")
                .outputQuality(QUALITY)
                .toOutputStream(outputStream);

        byte[] compressedImage = outputStream.toByteArray();

        // Если после сжатия размер все еще слишком большой
        if (compressedImage.length > MAX_SIZE_BYTES) {
            float scale = (float) MAX_SIZE_BYTES / compressedImage.length;
            outputStream = new ByteArrayOutputStream();
            Thumbnails.of(originalImage)
                    .scale(scale)
                    .outputFormat("jpg")
                    .outputQuality(QUALITY * scale)
                    .toOutputStream(outputStream);
            compressedImage = outputStream.toByteArray();
        }

        return compressedImage;
    }

    public static boolean needsCompression(MultipartFile file) throws IOException {
        return file.getSize() > MAX_SIZE_BYTES ||
                !Objects.equals(file.getContentType(), "image/jpeg");
    }
}
