package com.casestudy.digitalbankingopenapi.exception_handler;

import com.casestudy.digitalbankingopenapi.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.*;
import static com.casestudy.digitalbankingopenapi.constants.WordConstants.PATTERN;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldMissing.class)
    public ResponseEntity<Object> handleEmptySecurityQuestions(FieldMissing ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUSTOMER_FIELD_MISSING_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AttemptsFailedException.class)
    public ResponseEntity<Object> handleAttemptsFailedException(AttemptsFailedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(VALIDATE_OTP_ATTEMPTS_EXPIRED_OTP_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<Object> handleOtpExpiredException(OtpExpiredException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(VALIDATE_OTP_EXPIRED_OTP_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleEmptySecurityQuestions(NotFoundException ex) {
        ExceptionResponse exceptionResponse = null;
        if (ex.getType().equalsIgnoreCase("update customer")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_USERNAME_NOT_PRESENT_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("getSecurityImage")) {
            exceptionResponse = new ExceptionResponse(GET_CUSTOMER_SECURITY_IMAGE_IMAGE_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("getCustomerSecurityImage")) {
            exceptionResponse = new ExceptionResponse(GET_CUSTOMER_SECURITY_IMAGE_CUSTOMER_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("initiate otp")) {
            exceptionResponse = new ExceptionResponse(INITIATE_OTP_CUSTOMER_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("security Question")) {
            exceptionResponse = new ExceptionResponse(SECURITY_QUESTION_EMPTY_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("customerSecurityQuestion")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_QUESTION_EMPTY_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("customerSecurityImage")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_IMAGE_CUSTOMER_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("validate otp")) {
            exceptionResponse = new ExceptionResponse(VALIDATE_OTP_USERNAME_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        if (ex.getType().equalsIgnoreCase("ImageNotFound")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_IMAGE_IMAGE_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFound ex) {
        ExceptionResponse exceptionResponse = null;
        if (ex.getType().equalsIgnoreCase("update")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_UPDATE_USERNAME_NOT_PRESENT_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("otp")) {
            exceptionResponse = new ExceptionResponse(INITIATE_OTP_CUSTOMER_NOT_FOUND_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("securityQuestion")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_QUESTION_CUSTOMER_NOT_FOUND_ERROR_CODE, ex.getMessage());
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MandatoryFieldMissingException.class)
    public ResponseEntity<Object> handleMandatoryFieldMissing(MandatoryFieldMissingException ex) {
        ExceptionResponse exceptionResponse = null;
        if (ex.getType().equalsIgnoreCase("update customer")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_UPDATE_FIELD_MISSING_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("initiate otp")) {
            exceptionResponse = new ExceptionResponse(
                    FIELD_MISSING_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("customerSecurityQuestion")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_SECURITY_QUESTION_FIELD_MISSING_FOUND_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("customerSecurityImage")) {
            exceptionResponse = new ExceptionResponse(
                    CUSTOMER_SECURITY_IMAGE_FIELD_MISSING_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("validate otp")) {
            exceptionResponse = new ExceptionResponse(
                    FIELD_MISSING_ERROR_CODE, ex.getMessage());
        } else if (ex.getType().equalsIgnoreCase("getCustomerSecurityImage")) {
            exceptionResponse = new ExceptionResponse(
                    GET_CUSTOMER_SECURITY_IMAGE_FIELD_MISSING_ERROR_CODE, ex.getMessage());
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
            if (ex.getType().equalsIgnoreCase("add customer")) {
                exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_PHONE_NUMBER_ERROR_CODE, message);
            } else {
                exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_PHONE_NUMBER_ERROR_CODE, message);
            }
        } else if (message.equalsIgnoreCase("Invalid Email")) {
            if (ex.getType().equalsIgnoreCase("add customer")) {
                exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_EMAIL_ERROR_CODE, message);
            } else {
                exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_EMAIL_ERROR_CODE, message);
            }
        } else if (message.equalsIgnoreCase("Invalid Preferred Language")) {
            if (ex.getType().equalsIgnoreCase("add customer")) {
                exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, message);
            } else {
                exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, message);
            }
        } else if (message.equalsIgnoreCase("Invalid Username")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_USERNAME_ERROR_CODE, message);
        } else if (message.equalsIgnoreCase("Invalid Template Id")) {
            exceptionResponse = new ExceptionResponse(INITIATE_OTP_INVALID_TEMPLATE_ID_ERROR_CODE, message);
        } else if (message.equalsIgnoreCase("Security Image Caption Invalid")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_SECURITY_IMAGE_CAPTION_INVALID_ERROR_CODE, message);
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = null;
        String argument = "";
        String code = "";
        if (!ex.getAllErrors().isEmpty()) {
            argument = ex.getAllErrors().get(0).getArguments()[0].toString();
            code = ex.getAllErrors().get(0).getCode();
        }
        if (code.equalsIgnoreCase("NotEmpty") || code.equalsIgnoreCase("NotNull")) {
            FieldMissing customerFieldMissing = new FieldMissing();
            exceptionResponse = new ExceptionResponse(CUSTOMER_FIELD_MISSING_ERROR_CODE, customerFieldMissing.getMessage());
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("phoneNumber")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_PHONE_NUMBER_ERROR_CODE, "Invalid Phone Number");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("email")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_EMAIL_ERROR_CODE, "Invalid Email");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("preferredLanguage")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, "Invalid Preferred Language");
        } else if (code.equalsIgnoreCase(PATTERN) && argument.contains("userName")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_USERNAME_ERROR_CODE, "Invalid User Name");
        } else if (code.equalsIgnoreCase("size") && argument.contains("userName")) {
            exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_USERNAME_ERROR_CODE, "Invalid User Name");
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        ExceptionResponse exceptionResponse = null;
        if (Objects.nonNull(message) && message.contains("preferredLanguage")) {
            HttpMethod httpMethod = ((ServletWebRequest) request).getHttpMethod();
            if(Objects.nonNull(httpMethod) && httpMethod.toString().equals("PATCH")){
                exceptionResponse = new ExceptionResponse(CUSTOMER_UPDATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, "Invalid Preferred Language");
            }else if (Objects.nonNull(httpMethod) && httpMethod.toString().equals("POST")){
                exceptionResponse = new ExceptionResponse(CUSTOMER_CREATE_INVALID_PREFERRED_LANGUAGE_ERROR_CODE, "Invalid Preferred Language");
            }
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
