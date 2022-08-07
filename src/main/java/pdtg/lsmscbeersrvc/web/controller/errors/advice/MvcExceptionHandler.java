package pdtg.lsmscbeersrvc.web.controller.errors.advice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import pdtg.lsmscbeersrvc.web.controller.errors.ErrorResponse;
import pdtg.lsmscbeersrvc.web.controller.errors.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.util.*;
/**
 * Created by Diego T. 22-07-2022
 */

@Slf4j
@ControllerAdvice
public class MvcExceptionHandler {


    @Value("${pdtg.trace:false}")
    private boolean printStackTrace;

    public static final String TRACE = "trace";

    @ExceptionHandler(value = {ResourceAccessException.class})
    ResponseEntity<ErrorResponse> handleConnectException(HttpServletRequest request,ConnectException ex){

        log.error("handleConnectException, url={}, messages={}",
                request.getRequestURL(), ex.getMessage());
        return buildErrorResponse(ex,HttpStatus.BAD_GATEWAY,request);

    }
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request,NotFoundException ex){
        log.error("handleMethodArgumentNotValidException, url={}, message={}",
                request.getRequestURL(), ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request,
                                         MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException, url={}, message={}",
                request.getRequestURL(), ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error. Check 'errors' field for details"
        );

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            errorResponse.addValidationError(fieldError.getField(),
                    fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpServletRequest request,
                                               HttpRequestMethodNotSupportedException ex) {
        log.error("handleMethodNotAllowedException, url={}, message={}",
                request.getRequestURL(), ex.getMessage());

        return buildErrorResponse(ex,HttpStatus.METHOD_NOT_ALLOWED,request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public  ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex){
        log.error("handleDataIntegrityViolationException, url={}, message={}",
                request.getRequestURL(), ex.getMessage());
        return  buildErrorResponse((Exception) ExceptionUtils.getRootCause(ex),HttpStatus.BAD_REQUEST,request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<String>> handleAll(HttpServletRequest request, Exception ex){
        List<String> errorsList = new ArrayList<>();
        errorsList.add("Unknown Error Occurred at: "+request.getRequestURL()+" message: "+ex.getMessage());
        return new ResponseEntity<>(errorsList,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception ex,
            HttpStatus httpStatus,
            HttpServletRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                ex.getMessage());

        if(printStackTrace && isTraceOn(request)){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(ex));
        }
        return  ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(HttpServletRequest request){
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }
}
