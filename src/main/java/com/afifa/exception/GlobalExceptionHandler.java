package com.afifa.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
    public class GlobalExceptionHandler {
         @ExceptionHandler(EventNotFoundException.class)
        public ResponseEntity<String> handleEventNotFound(EventNotFoundException ex){
            String message= ex.getMessage();

             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body(message);}
        @ExceptionHandler(InsufficientTicketsException.class)
        public ResponseEntity<String> handleInsufficientTickets(InsufficientTicketsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        @ExceptionHandler(BookingNotFoundException.class)
        public ResponseEntity<String> handleBookingNotFound(BookingNotFoundException ex){
             String message= ex.getMessage();
             return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(message);
        }
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
            String message= ex.getMessage();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(message);
        }
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<String> handleValidationException(
                ConstraintViolationException ex) {

            String message = ex.getConstraintViolations()
                    .stream()
                    .map(violation ->
                            violation.getPropertyPath() + ": "
                                    + violation.getMessage())
                    .collect(Collectors.joining("\n"));

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }


}
