package com.magic.spring;

import com.magic.spring.service.exception.InsertException;
import com.magic.spring.service.exception.ServiceException;
import com.magic.spring.service.exception.UsernameDuplicatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;

/**
 * @author Hsy
 * @Description We use the @ControllerAdvice to handle the exception globally.
 * This way is better than building a base controller and let other controllers
 * to extend it. The @ControllerAdvice will be applied to all controllers.
 * More details: <a href="https://www.jianshu.com/p/12e1a752974d">https://www.jianshu.com/p/12e1a752974d</a>
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    /**
     * @Description This method can handle the exception to {@link ServiceException} and its subclasses.
     * @param e ServiceException
     * @return ResponseEntity
     */
    @ExceptionHandler({ServiceException.class, NoSuchAlgorithmException.class})
    @ResponseBody
    public ResponseEntity<?> handleRegisterException(ServiceException e) {
        e.printStackTrace();
        if (e instanceof UsernameDuplicatedException) {
            return ResponseEntity.badRequest().body("Username already exists.");
        } else if (e instanceof InsertException) {
            return ResponseEntity.badRequest().body("User insert failed.");
        }
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * @Description This method can handle the other exceptions.
     * @param e Throwable
     * @return ResponseEntity
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<?> exceptionHandle(Throwable e) {
        e.printStackTrace();
        if (e instanceof NoSuchAlgorithmException) {
            return ResponseEntity.badRequest().body("Password encryption failed. The problem is in the server.");
        }
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
