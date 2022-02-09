package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityQuestions;
import com.casestudy.digitalbankingopenapi.entity.SecurityQuestion;
import com.casestudy.digitalbankingopenapi.exception.SecurityQuestionsNotFound;
import com.casestudy.digitalbankingopenapi.repository.CustomerSecurityQuestionsRepo;
import com.casestudy.digitalbankingopenapi.repository.SecurityQuestionsRepo;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.GetCustomerSecurityQuestionResponseDto;
import openapi.model.GetSecurityQuestionsResponseDto;
import openapi.model.SecurityQuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SecurityQuestionsService {

    private SecurityQuestionsRepo securityQuestionsRepo;
    private RequestValidation requestValidation;

    public SecurityQuestionsService(SecurityQuestionsRepo securityQuestionsRepo, RequestValidation requestValidation) {
        this.securityQuestionsRepo = securityQuestionsRepo;
        this.requestValidation = requestValidation;
    }

    public ResponseEntity<GetSecurityQuestionsResponseDto> getAllSecurityQuestions() {
        List<SecurityQuestion> list = securityQuestionsRepo.findAll();
        if (list.isEmpty()) {
            throw new SecurityQuestionsNotFound();
        }
        GetSecurityQuestionsResponseDto listResponse = new GetSecurityQuestionsResponseDto();
        list.forEach(l -> {
            SecurityQuestionDto securityQuestionDto=l.toDto();
            listResponse.addSecurityQuestionsItem(securityQuestionDto);
        });
        return ResponseEntity.ok().body(listResponse);
    }

    public ResponseEntity<GetCustomerSecurityQuestionResponseDto> getSecurityQuestionsByUsername(String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "securityQuestion");
        List<CustomerSecurityQuestions> customerSecurityQuestionsList = customer.getCustomerSecurityQuestionsList();
        if (customerSecurityQuestionsList.isEmpty()) {
            throw new SecurityQuestionsNotFound();
        }
        GetCustomerSecurityQuestionResponseDto response = new GetCustomerSecurityQuestionResponseDto();
        customerSecurityQuestionsList.forEach(l -> response.addSecurityQuestionsItem(l.toDto()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
