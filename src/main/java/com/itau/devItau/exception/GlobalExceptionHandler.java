package com.itau.devItau.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itau.devItau.dto.ErrorResponse;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFound(TransactionNotFoundException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleTransactionNotFound =======");
        logger.error("Request processing failed due to invalid endpoint or filter configuration.", exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(TransactionBusinessException.class)
    public ResponseEntity<ErrorResponse> handleTransactionBusinessException(TransactionBusinessException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleTransactionBusinessException =======");
        logger.error("Request processing failed due to business rule violation.", exception);

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleTransactionMissingParamException(MissingServletRequestParameterException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleTransactionMissingParamException =======");
        logger.error("Request processing failed due to missing required parameter: {}", exception.getParameterName());

        String message = "O parâmetro '" + exception.getParameterName() + "' é obrigatório.";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTransactionArgumentTypeMissMatchException(MethodArgumentTypeMismatchException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleTransactionArgumentTypeMissMatchException =======");
        logger.error("Request processing failed due to Argument type Mismatch at parameter: {} | expected: {}", exception.getName(), exception.getRequiredType().getSimpleName());
        String message = "O parametro '" + exception.getName()
                + "' deve ser do tipo: " + exception.getRequiredType().getSimpleName();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleHttpMessageNotReadableException =======");
        logger.error("Invalid request body. The request payload could not be parsed.", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Assegure que o corpo da requisição e seus atributos estão corretos."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error("======= GLOBAL_EXCEPTION: handleMethodArgumentNotValidException =======");
        logger.error("Request validation failed. One or more fields are invalid.");
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .build();
    }
}
