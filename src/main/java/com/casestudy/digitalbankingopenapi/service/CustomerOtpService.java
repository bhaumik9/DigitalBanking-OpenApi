package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import com.casestudy.digitalbankingopenapi.exception.AttemptsFailedException;
import com.casestudy.digitalbankingopenapi.exception.InvalidFieldException;
import com.casestudy.digitalbankingopenapi.exception.OtpExpiredException;
import com.casestudy.digitalbankingopenapi.mapper.OtpMapperImpl;
import com.casestudy.digitalbankingopenapi.repository.CustomerOtpRepo;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import lombok.NoArgsConstructor;
import openapi.model.InitiateOtpRequestDto;
import openapi.model.ValidateOtpRequestDto;
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
    private OtpMapperImpl otpMapper;

    @Autowired
    public CustomerOtpService(CustomerService customerService, CustomerRepo customerRepo, CustomerOtpRepo customerOtpRepo, RequestValidation requestValidation, OtpMapperImpl otpMapper) {
        this.customerService = customerService;
        this.customerRepo = customerRepo;
        this.customerOtpRepo = customerOtpRepo;
        this.requestValidation = requestValidation;
        this.otpMapper = otpMapper;
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
        CustomerOtp customerOtp = otpMapper.mapToEntity(customerOtpDto, LocalDateTime.now());
        customerOtp.setOtpMessage(message);
        customerOtp.setRetries(0);
        customerOtp.setOtp(otp);
        return customerOtp;
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

    public ResponseEntity<Void> validateOtp(ValidateOtpRequestDto validateOtpRequestDto) {
        Customer customer = requestValidation.validateUserNameInDatabase(validateOtpRequestDto.getUserName(), "validate otp");
        requestValidation.validateOtpIsNullOrEmpty(validateOtpRequestDto.getOtp());
        CustomerOtp customerOtp = customer.getCustomerOtp();
        int retries = customerOtp.getRetries();
        if(!customer.getCustomerOtp().getOtp().equals(validateOtpRequestDto.getOtp())){
            retries++;
            customerOtp.setRetries(retries);
            customerOtpRepo.save(customerOtp);
            throw new InvalidFieldException("invalidate otp", "validate otp");
        }
        if(retries>2){
            throw new AttemptsFailedException("You have exceeded your attempts");
        }
        if(customerOtp.getExpiresOn().isBefore(LocalDateTime.now())){
            throw new OtpExpiredException("Otp entered by you is expired");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
