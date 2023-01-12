package com.parkinglot.parkinglot.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<?> bayIdNotFoundException(ParkingLotException parkingLotException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parkingLotException.getMessage());
    }
}
