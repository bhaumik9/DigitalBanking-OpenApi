package com.casestudy.digitalbankingopenapi.exception_handler;

import com.casestudy.digitalbankingopenapi.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.*;
import static com.casestudy.digitalbankingopenapi.constants.WordConstants.PATTERN;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerFieldMissing.class)
    public ResponseEntity<Object> handleEmptySecurityQuestions(CustomerFieldMissing ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUSTOMER_FIELD_MISSING_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomerSecurityQuestionsNotFound.class)
    public ResponseEntity<Object> handleEmptySecurityQuestions(CustomerSecurityQuestionsNotFound ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_QUESTION_EMPTY_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecurityQuestionsNotFound.class)
    public ResponseEntity<Object> handleEmptySecurityQuestions(SecurityQuestionsNotFound ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(SECURITY_QUESTION_EMPTY_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFound ex) {
        ExceptionResponse exceptionResponse = null;
        if (ex.getType().equalsIgnoreCase("update")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_UPDATE_NOT_PRESENT_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("otp")) {
            exceptionResponse = new ExceptionResponse(INVALID_CUSTOMER_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("securityQuestion")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_QUESTION_CUSTOMER_NOT_FOUND__ERROR_CODE, ex.getMessage());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MandatoryFieldMissingException.class)
    public ResponseEntity<Object> handleMandatoryFieldMissing(MandatoryFieldMissingException ex) {
        ExceptionResponse exceptionResponse = null;
        if (ex.getType().equalsIgnoreCase("update")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_UPDATE_FIELD_MISSING_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("otp")) {
            exceptionResponse = new ExceptionResponse(
                    FIELD_MISSING_ERROR_CODE, ex.getMessage());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Object> handleDuplicateUsername(DuplicateUsernameException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                CUSTOMER_DUPLICATE_USERNAME_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<Object> handleInvalidField(InvalidFieldException ex) {

        String message = ex.getMessage();
        ExceptionResponse exceptionResponse = null;
        if (message.equalsIgnoreCase("Invalid Phone Number")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_PHONE_NUMBER_ERROR_CODE, message);
        } else if (message.equalsIgnoreCase("Invalid Email")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_EMAIL_ERROR_CODE, message);
        } else if (message.equalsIgnoreCase("Invalid Preferred Language")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, message);
        } else if (message.equalsIgnoreCase("Invalid Template Id")) {
            exceptionResponse = new ExceptionResponse(INVALID_TEMPLATE_ID_ERROR_CODE, message);
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = null;
        String argument="";
        String code="";
        if(!ex.getAllErrors().isEmpty()){
            argument = ex.getAllErrors().get(0).getArguments()[0].toString();
            code = ex.getAllErrors().get(0).getCode();
        }
        if (code.equalsIgnoreCase("NotEmpty") || code.equalsIgnoreCase("NotNull")) {
            CustomerFieldMissing customerFieldMissing = new CustomerFieldMissing();
            exceptionResponse = new ExceptionResponse(CUSTOMER_FIELD_MISSING_ERROR_CODE, customerFieldMissing.getMessage());
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("phoneNumber")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_INVALID_PHONE_NUMBER_ERROR_CODE, "Invalid Phone Number");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("email")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_INVALID_EMAIL_ERROR_CODE, "Invalid Email");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("preferredLanguage")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_INVALID_LANGUAGE_ERROR_CODE, "Invalid Preferred Language");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("userName")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_INVALID_USERNAME_ERROR_CODE, "Invalid User Name");
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
