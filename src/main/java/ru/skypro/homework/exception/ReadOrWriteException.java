package ru.skypro.homework.exception;
/**
 * Исключение, возникающее при ошибках во время чтения или записи на диск
 */

public class ReadOrWriteException extends RuntimeException{
    public ReadOrWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
