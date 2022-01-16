package by.htp.ramanouski.taskmanager.controller.exception;

import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.ui.model.response.error.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class TaskManagerExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(Exception exception,
                                                             WebRequest request){

        ErrorResponse errorMessage = new ErrorResponse(new Date(), exception.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception exception,
                                                       WebRequest request){

        ErrorResponse errorMessage = new ErrorResponse(new Date(), exception.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
