package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.service.ImageService;
import java.io.*;
import java.nio.file.Path;

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * Сохранение картинки
     */
    @Override
    public void saveFile(Path path, byte[] source) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             BufferedOutputStream bof = new BufferedOutputStream(fos, 1024);
             BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(source), 1024)) {
            int i;
            while ((i = bis.read()) != -1) {
                bof.write(i);
            }
        }
    }

    /**
     * Чтение картинки
     */

    @Override
    public byte[] readFile(Path path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path.toFile());
             BufferedInputStream bis = new BufferedInputStream(fis, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int i;
            while ((i = bis.read()) != -1) {
                baos.write(i);
            }
            return baos.toByteArray();
        }
    }
}
