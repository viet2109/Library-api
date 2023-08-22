package com.matcha.nlulibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleAllException(Exception ex) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()),HttpStatus.NOT_FOUND);
    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage TodoException() {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Đối tượng không tồn tại");
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage  userAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage(ex.getMessage());
        err.setStatusCode(HttpStatus.CONFLICT.value());

        return err;
    }
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage userNotExistsException(UserNotExistException ex) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage(ex.getMessage());
        err.setStatusCode(HttpStatus.FORBIDDEN.value());

        return err;
    }
}
