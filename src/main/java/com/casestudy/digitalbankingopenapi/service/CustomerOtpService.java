package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import com.casestudy.digitalbankingopenapi.repository.CustomerOtpRepo;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import lombok.NoArgsConstructor;
import openapi.model.InitiateOtpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;
import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.REGISTRATION;

@Service
@NoArgsConstructor
@PropertySource("classpath:application.yml")
public class CustomerOtpService {

    @Value("${otp.default}")
    private String messageDefault;
    @Value("${otp.registration}")
    private String messageRegistration;
    @Value("${otp.login}")
    private String messageLogin;
    private CustomerService customerService;
    private CustomerRepo customerRepo;
    private CustomerOtpRepo customerOtpRepo;
    private RequestValidation requestValidation;

    @Autowired
    public CustomerOtpService(CustomerService customerService, CustomerRepo customerRepo, CustomerOtpRepo customerOtpRepo, RequestValidation requestValidation) {
        this.customerService = customerService;
        this.customerRepo = customerRepo;
        this.customerOtpRepo = customerOtpRepo;
        this.requestValidation = requestValidation;
    }

    public CustomerOtp getData(InitiateOtpRequestDto customerOtpDto) {
        String otp = customerService.generateOtp();
        String templateId = customerOtpDto.getTemplateId();
        String message;
        if (!Objects.isNull(templateId) && templateId.equals(REGISTRATION)) {
            message = messageRegistration.concat(" ").concat(otp);
        } else if (!Objects.isNull(templateId) && templateId.equals(LOGIN)) {
            message = messageLogin.concat(" ").concat(otp);
        } else {
            message = messageDefault.concat(" ").concat(otp);
        }
        LocalDateTime otpTimeCreated = LocalDateTime.now();
        LocalDateTime otpTimeExpired = otpTimeCreated.plusMinutes(5);
        return new CustomerOtp(message, otp, 0, otpTimeExpired, otpTimeCreated, customerOtpDto.getUserName());
    }

    public void update(CustomerOtp customerOtp) {
        Customer customer = customerRepo.findByUserName(customerOtp.getUserName());
        CustomerOtp customerOtpDb = customer.getCustomerOtp();
        if (customerOtpDb == null) {
            customerOtpDb = new CustomerOtp();
            customerOtpDb.setCustomer(customer);
        }
        customerOtpDb.setOtpMessage(customerOtp.getOtpMessage());
        customerOtpDb.setOtp(customerOtp.getOtp());
        customerOtpDb.setRetries(customerOtp.getRetries());
        customerOtpDb.setCreatedOn(customerOtp.getCreatedOn());
        customerOtpDb.setExpiresOn(customerOtp.getExpiresOn());
        customerOtpRepo.save(customerOtpDb);
    }

    public ResponseEntity<Void> initiateOtpRequest(InitiateOtpRequestDto initiateOtpRequestDto) {
        requestValidation.validateInitiateOtpRequest(initiateOtpRequestDto);
        CustomerOtp customerOtp = getData(initiateOtpRequestDto);
        update(customerOtp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
