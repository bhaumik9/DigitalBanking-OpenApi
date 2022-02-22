package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.service.CustomerOtpService;
import com.casestudy.digitalbankingopenapi.service.CustomerService;
import com.casestudy.digitalbankingopenapi.service.SecurityImageService;
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
    private SecurityImageService securityImageService;

    @Autowired
    public MyController(CustomerService customerService, CustomerOtpService customerOtpService, SecurityQuestionsService securityQuestionsService, SecurityImageService securityImageService) {
        this.customerService = customerService;
        this.customerOtpService = customerOtpService;
        this.securityQuestionsService = securityQuestionsService;
        this.securityImageService = securityImageService;
    }
    @Override
    public ResponseEntity<Void> initiateOtp(@RequestBody InitiateOtpRequestDto initiateOtpRequestDto) {
        return customerOtpService.initiateOtpRequest(initiateOtpRequestDto);
    }

    @Override
    public ResponseEntity<Void> patchCustomerByUserName(String username, PatchCustomerRequestDto patchCustomerRequestDto) {
        return customerService.update(patchCustomerRequestDto, username);
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

    @Override
    public ResponseEntity<Void> saveSecurityImageById(String username, CreateCustomerSecurityImageRequestDto createCustomerSecurityImageRequestDto) {
        return securityImageService.addImageByUsername(username,createCustomerSecurityImageRequestDto);
    }

    @Override
    public ResponseEntity<GetCustomerSecurityImageResponseDto> getSecurityImageByUserName(String username) {
       return securityImageService.getImageByUsername(username);
    }


}
