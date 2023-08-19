package ru.skypro.homework.exception;
/**
 * Исключение, возникающее при отсутствии прав доступа
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
