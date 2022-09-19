package com.opencourse.authusermanagement.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;
import com.opencourse.authusermanagement.exceptions.EmailAlreadyExistsException;
import com.opencourse.authusermanagement.exceptions.EmailConfirmationException;
import com.opencourse.authusermanagement.exceptions.ExpiredTokenException;
import com.opencourse.authusermanagement.exceptions.UserNotFoundException;

import lombok.Data;

@RestControllerAdvice
public class CustomExceptionsHandler {
    

    @ExceptionHandler({CustomAuthenticationException.class})
    public ResponseEntity<ApiError> handleCustomAuthenticationException(CustomAuthenticationException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setErrorMsg(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error,error.getStatus());
    }
    
    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setErrorMsg(ex.getMessage());
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler({EmailConfirmationException.class})
    public ResponseEntity<ApiError> handleEmailConfirmationException(EmailConfirmationException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setErrorMsg(ex.getMessage());
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public ResponseEntity<ApiError> handleExpiredTokenException(ExpiredTokenException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setErrorMsg(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setErrorMsg(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request){
        List<String> errors=new ArrayList<String>();
        for(FieldError error:ex.getBindingResult().getFieldErrors()){
            errors.add(error.getDefaultMessage());
        }
        ApiError apiError=new ApiError();
        apiError.setErrors(errors);
        apiError.setErrorMsg(ex.getLocalizedMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(apiError,apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
            violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError apiError = new ApiError();
        apiError.setErrors(errors);
        apiError.setErrorMsg(ex.getLocalizedMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    
}

@Data
class ApiError{
    private HttpStatus status;
    private String errorMsg;
    private List<String> errors;
}
