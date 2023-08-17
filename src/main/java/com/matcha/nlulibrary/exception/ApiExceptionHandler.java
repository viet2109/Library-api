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
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new ErrorMessage(10000, ex.getLocalizedMessage());
//    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
//    @ExceptionHandler(IndexOutOfBoundsException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ErrorMessage TodoException(Exception ex, WebRequest request) {
//        return new ErrorMessage(10100, "Đối tượng không tồn tại");
//    }
    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage(ex.getMessage());
        err.setStatusCode(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorMessage> userNotExistsException(UserNotExistException ex) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage(ex.getMessage());
        err.setStatusCode(HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }
}
