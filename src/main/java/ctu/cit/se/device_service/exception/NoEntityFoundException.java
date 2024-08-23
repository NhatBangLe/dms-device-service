package ctu.cit.se.device_service.exception;

import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

@NoArgsConstructor
public class NoEntityFoundException extends NoSuchElementException {

    public NoEntityFoundException(String message) {
        super(message);
    }

}
