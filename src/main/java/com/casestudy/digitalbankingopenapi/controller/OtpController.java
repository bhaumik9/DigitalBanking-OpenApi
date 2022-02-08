package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.dto.CustomerOtpDto;
import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import com.casestudy.digitalbankingopenapi.service.CustomerOtpService;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-service")
public class OtpController {

    private CustomerOtpService customerOtpService;
    private RequestValidation requestValidation;

    @Autowired
    public OtpController(CustomerOtpService customerOtpService, RequestValidation requestValidation) {
        this.customerOtpService = customerOtpService;
        this.requestValidation = requestValidation;
    }

    @PostMapping("/client-api/v1/otp/initiate")
    public ResponseEntity<Object> initiateOtp(@RequestBody CustomerOtpDto customerOtpDto) {
        requestValidation.validateInitiateOtpRequest(customerOtpDto);
        CustomerOtp customerOtp = customerOtpService.getData(customerOtpDto);
        customerOtpService.update(customerOtp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
