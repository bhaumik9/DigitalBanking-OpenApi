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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityQuestionsService {

    private SecurityQuestionsRepo securityQuestionsRepo;
    private RequestValidation requestValidation;
    private CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    public SecurityQuestionsService(SecurityQuestionsRepo securityQuestionsRepo, RequestValidation requestValidation, CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo) {
        this.securityQuestionsRepo = securityQuestionsRepo;
        this.requestValidation = requestValidation;
        this.customerSecurityQuestionsRepo = customerSecurityQuestionsRepo;
    }

    public ResponseEntity<Object> getAllSecurityQuestions() {
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

    public ResponseEntity<Object> addQuestion(String question) {
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setSecurityQuestionText(question);
        securityQuestionsRepo.save(securityQuestion);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getSecurityQuestionsByUsername(String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "securityQuestion");
        List<CustomerSecurityQuestions> customerSecurityQuestionsList = customer.getCustomerSecurityQuestionsList();
        if (customerSecurityQuestionsList.isEmpty()) {
            throw new SecurityQuestionsNotFound();
        }
        GetCustomerSecurityQuestionResponseDto response = new GetCustomerSecurityQuestionResponseDto();
        customerSecurityQuestionsList.forEach(l -> response.addSecurityQuestionsItem(l.toDto()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> addSecurityQuestionsByUsername(String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "securityQuestion");
        List<CustomerSecurityQuestions> customerSecurityQuestions = setSecurityQuestions(customer);
        customer.setCustomerSecurityQuestionsList(customerSecurityQuestions);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<CustomerSecurityQuestions> setSecurityQuestions(Customer customer) {
        List<CustomerSecurityQuestions> list = new ArrayList<>();
        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions();
        Optional<SecurityQuestion> securityQuestion = securityQuestionsRepo.findById("1bf0e080-fb82-49c4-8039-d6e6977d093e");
        if (securityQuestion.isPresent()) {
            customerSecurityQuestions.setCustomer(customer);
            customerSecurityQuestions.setSecurityQuestion(securityQuestion.get());
            customerSecurityQuestions.setSecurityQuestionAnswer("Mustang GT");
            customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        }
        list.add(customerSecurityQuestions);
        return list;
    }
}
