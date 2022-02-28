package com.casestudy.digitalbankingopenapi.validation;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityImages;
import com.casestudy.digitalbankingopenapi.entity.SecurityImages;
import com.casestudy.digitalbankingopenapi.exception.*;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.repository.CustomerSecurityImagesRepo;
import com.casestudy.digitalbankingopenapi.repository.SecurityImageRepo;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.CreateCustomerSecurityImageRequestDto;
import openapi.model.InitiateOtpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;
import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.REGISTRATION;

@Component
public class RequestValidation {

    private CustomerRepo customerRepo;
    private SecurityImageRepo securityImageRepo;
    private CustomerSecurityImagesRepo customerSecurityImagesRepo;

    @Autowired
    public RequestValidation(CustomerRepo customerRepo, SecurityImageRepo securityImageRepo, CustomerSecurityImagesRepo customerSecurityImagesRepo) {
        this.customerRepo = customerRepo;
        this.securityImageRepo = securityImageRepo;
        this.customerSecurityImagesRepo = customerSecurityImagesRepo;
    }

    public void validateInitiateOtpRequest(InitiateOtpRequestDto customerOtpDto) {
        validateUserNameInDatabase(customerOtpDto.getUserName(), "initiate otp");
        isValidTemplateId(customerOtpDto.getTemplateId());
    }

    public Customer validateUserNameInDatabase(String userName, String type) {
        if (Objects.isNull(userName)) {
            throw new MandatoryFieldMissingException(type);
        }
        Customer customer = customerRepo.findByUserName(userName);
        if (Objects.isNull(customer)) {
            throw new NotFoundException("Username not found in database",type);
        }
        return customer;
    }

    public void isValidTemplateId(String templateId) {
        if (Objects.isNull(templateId) || templateId.isEmpty() || templateId.equals(REGISTRATION) || templateId.equals(LOGIN)) {
            return;
        }
        throw new InvalidFieldException("Invalid Template Id", "initiate otp");
    }

    public void validateCustomer(CreateCustomerRequestDto customerDto) {
        validateMissingFields(customerDto);
        validateEmail(customerDto.getEmail(),"add customer");
        validatePhoneNumber(customerDto.getPhoneNumber(),"add customer");
        validateUsernameByRegex(customerDto.getUserName(),"add customer");
        validatePreferredLanguage(customerDto.getPreferredLanguage().toString(),"add customer");
        validateDuplicateUsername(customerDto.getUserName());
    }

    private void validateMissingFields(CreateCustomerRequestDto customerDto) {
        if (customerDto.getUserName().isEmpty()) {
            throw new FieldMissing("Username");
        }
        if (customerDto.getFirstName().isEmpty()) {
            throw new FieldMissing("First Name");
        }
        if (customerDto.getLastName().isEmpty()) {
            throw new FieldMissing("Last Name");
        }
        if (Objects.isNull(customerDto.getEmail()) || customerDto.getEmail().isEmpty()) {
            throw new FieldMissing("Email");
        }
        if (customerDto.getPhoneNumber().isEmpty()) {
            throw new FieldMissing("Phone Number");
        }
        if (Objects.isNull(customerDto.getPreferredLanguage()) || customerDto.getPreferredLanguage().toString().isEmpty()) {
            throw new FieldMissing("Preferred Language");
        }
    }

    public boolean validateUsernameByRegex(String username,String type) {
        if (!username.matches("^[A-Za-z][A-Za-z0-9_]{7,29}$")) {
            throw new InvalidFieldException("Invalid Username", type);
        }
        return Boolean.TRUE;
    }

    public boolean validatePhoneNumber(String phoneNumber,String type) {
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidFieldException("Invalid Phone Number", type);
        }
        return Boolean.TRUE;
    }

    public boolean validateEmail(String email,String type) {
        if (!email.matches("[\\w+.]*@\\w+.[com|in]{2,3}")) {
            throw new InvalidFieldException("Invalid Email", type);
        }
        return Boolean.TRUE;
    }

    public boolean validatePreferredLanguage(String preferredLanguage,String type) {
        if (!((preferredLanguage.equals("EN")) || (preferredLanguage.equals("DE")) || (preferredLanguage.equals("FR")))) {
            throw new InvalidFieldException("Invalid Preferred Language", type);
        }
        return Boolean.TRUE;
    }

    public boolean validateStatus(String status) {
        if (status.equals("ACTIVE") || status.equals("INACTIVE") || status.equals("PENDING")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void validateDuplicateUsername(String username) {
        if (customerRepo.existsByUserName(username)) {
            throw new DuplicateUsernameException();
        }
    }

    public SecurityImages validateSecurityImage(CreateCustomerSecurityImageRequestDto createCustomerSecurityImageRequestDto,String type) {
        if (Objects.isNull(createCustomerSecurityImageRequestDto.getSecurityImageCaption()) || createCustomerSecurityImageRequestDto.getSecurityImageCaption().isEmpty() || createCustomerSecurityImageRequestDto.getSecurityImageCaption().length() < 3) {
            throw new InvalidFieldException("Security Image Caption Invalid", type);
        }
        Optional<SecurityImages> optionalSecurityImage = securityImageRepo.findById(createCustomerSecurityImageRequestDto.getSecurityImageId());
        SecurityImages securityImage;
        if (optionalSecurityImage.isPresent()) {
            securityImage = optionalSecurityImage.get();
            return securityImage;
        }else {
            throw new NotFoundException("Security Image Not Found","ImageNotFound");
        }
    }

    public void validateExistingImage(String id,Customer customer) {
        CustomerSecurityImages customerSecurityImage = customerSecurityImagesRepo.findByCustomerId(id);
        if(Objects.isNull(customerSecurityImage)){
            return;
        }else {
            customerSecurityImagesRepo.delete(customerSecurityImage);
        }
    }

    public void validateOtpIsNullOrEmpty(String otp) {
        if(Objects.isNull(otp) || otp.isEmpty()){
            throw new FieldMissing("validate otp");
        }
    }
}
