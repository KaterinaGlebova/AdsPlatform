package ru.skypro.homework.service;

import java.io.IOException;
import java.nio.file.Path;
/**
 * Сервис для работы с изображениями
 */

public interface ImageService {
    void saveFile(Path path, byte[] source) throws IOException;
    byte[] readFile(Path path) throws IOException;
}
