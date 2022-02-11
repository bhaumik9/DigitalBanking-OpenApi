package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.service.CustomerOtpService;
import com.casestudy.digitalbankingopenapi.service.CustomerService;
import com.casestudy.digitalbankingopenapi.service.SecurityQuestionsService;
import openapi.api.ClientApiApi;
import openapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("/customer-service")
public class MyController implements ClientApiApi{

    private CustomerService customerService;
    private CustomerOtpService customerOtpService;
    private SecurityQuestionsService securityQuestionsService;

    @Autowired
    public MyController(CustomerService customerService, CustomerOtpService customerOtpService, SecurityQuestionsService securityQuestionsService) {
        this.customerService = customerService;
        this.customerOtpService = customerOtpService;
        this.securityQuestionsService = securityQuestionsService;
    }
    @Override
    public ResponseEntity<Void> initiateOtp(@RequestBody InitiateOtpRequestDto initiateOtpRequestDto) {
        customerOtpService.initiateOtpRequest(initiateOtpRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> patchCustomerByUserName(String username, PatchCustomerRequestDto patchCustomerRequestDto) {
        customerService.update(patchCustomerRequestDto, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreateCustomerResponseDto> postCustomers(CreateCustomerRequestDto createCustomerRequestDto) {
        CreateCustomerResponseDto createCustomerResponseDto = customerService.add(createCustomerRequestDto);
        ResponseEntity<CreateCustomerResponseDto> response;
        if (!Objects.isNull(createCustomerResponseDto)) {
            response = new ResponseEntity<>(createCustomerResponseDto, HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<GetSecurityQuestionsResponseDto> getSecurityQuestions() {
        return securityQuestionsService.getAllSecurityQuestions();
    }

    @Override
    public ResponseEntity<GetCustomerSecurityQuestionResponseDto> getSecurityQuestionsByUserName(String username) {
        return securityQuestionsService.getSecurityQuestionsByUsername(username);
    }
}
