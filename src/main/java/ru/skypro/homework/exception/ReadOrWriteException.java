package ru.skypro.homework.exception;

public class ReadOrWriteException extends RuntimeException{
    public ReadOrWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
