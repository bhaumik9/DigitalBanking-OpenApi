package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import com.casestudy.digitalbankingopenapi.entity.SecurityQuestion;
import com.casestudy.digitalbankingopenapi.exception.NotFoundException;
import com.casestudy.digitalbankingopenapi.exception.SecurityQuestionsNotFound;
import com.casestudy.digitalbankingopenapi.mapper.SecurityQuestionListMapper;
import com.casestudy.digitalbankingopenapi.repository.SecurityQuestionsRepo;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.GetCustomerSecurityQuestionResponseDto;
import openapi.model.GetSecurityQuestionsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SecurityQuestionsService {

    private SecurityQuestionsRepo securityQuestionsRepo;
    private RequestValidation requestValidation;
    private SecurityQuestionListMapper securityQuestionListMapper;

    public SecurityQuestionsService(SecurityQuestionsRepo securityQuestionsRepo, RequestValidation requestValidation, SecurityQuestionListMapper securityQuestionListMapper) {
        this.securityQuestionsRepo = securityQuestionsRepo;
        this.requestValidation = requestValidation;
        this.securityQuestionListMapper = securityQuestionListMapper;
    }

    public ResponseEntity<GetSecurityQuestionsResponseDto> getAllSecurityQuestions() {
        List<SecurityQuestion> list = securityQuestionsRepo.findAll();
        if (list.isEmpty()) {
            throw new NotFoundException("Empty List","security Question");
        }
        GetSecurityQuestionsResponseDto getSecurityQuestionsResponseDto = securityQuestionListMapper.mapToSecurityQuestionsResponseDto(list);
        return ResponseEntity.ok().body(getSecurityQuestionsResponseDto);
    }

    public ResponseEntity<GetCustomerSecurityQuestionResponseDto> getSecurityQuestionsByUsername(String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "customerSecurityQuestion");
        List<CustomerSecurityQuestions> customerSecurityQuestionsList = customer.getCustomerSecurityQuestionsList();
        if (customerSecurityQuestionsList.isEmpty()) {
            throw new NotFoundException("Empty List","customer security Question");
        }
        GetCustomerSecurityQuestionResponseDto response = securityQuestionListMapper.mapToCustomerSecurityQuestionResponseDto(customerSecurityQuestionsList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
