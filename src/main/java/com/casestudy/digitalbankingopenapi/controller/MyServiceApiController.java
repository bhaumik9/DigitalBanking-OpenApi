package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.service.CustomerOtpService;
import openapi.api.ServiceApiApi;
import openapi.model.ValidateOtpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyServiceApiController implements ServiceApiApi {

    @Autowired
    CustomerOtpService customerOtpService;
    @Override
    public ResponseEntity<Void> validateOtp(ValidateOtpRequestDto validateOtpRequestDto) {
        return customerOtpService.validateOtp(validateOtpRequestDto);
    }
}
