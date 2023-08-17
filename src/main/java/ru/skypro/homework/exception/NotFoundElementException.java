package ru.skypro.homework.exception;

public class NotFoundElementException extends RuntimeException {
    public NotFoundElementException(long id, Class<?> clazz) {
        super(String.format("%s not found by id=%d", clazz.getSimpleName(), id));
    }
}
