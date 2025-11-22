package com.ecommerce.walletservice.exception;

import com.ecommerce.walletservice.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(TransactionCreationException.class)
    public ResponseEntity<ExceptionResponseDto> handleTransactionCreationException(Exception e) {
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setMessage(e.getMessage());
        response.setExceptionType(e.getClass().getSimpleName());
        response.setStackTrace(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(LedgerRetrievalException.class)
    public ResponseEntity<ExceptionResponseDto> handleLedgerRetrievalException(Exception e) {
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setMessage(e.getMessage());
        response.setExceptionType(e.getClass().getSimpleName());
        response.setStackTrace(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
