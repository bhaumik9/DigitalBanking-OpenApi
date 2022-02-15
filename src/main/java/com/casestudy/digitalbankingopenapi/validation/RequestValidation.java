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
        validateUserNameInDatabase(customerOtpDto.getUserName(), "otp");
        isValidTemplateId(customerOtpDto.getTemplateId());
    }

    public Customer validateUserNameInDatabase(String userName, String type) {
        if (Objects.isNull(userName)) {
            throw new MandatoryFieldMissingException(type);
        }
        Customer customer = customerRepo.findByUserName(userName);
        if (Objects.isNull(customer)) {
            throw new NotFoundException("",type);
        }
        return customer;
    }

    public void isValidTemplateId(String templateId) {
        if (Objects.isNull(templateId) || templateId.isEmpty() || templateId.equals(REGISTRATION) || templateId.equals(LOGIN)) {
            return;
        }
        throw new InvalidFieldException("Invalid Template Id");
    }

    public void validateCustomer(CreateCustomerRequestDto customerDto) {
        validateMissingFields(customerDto);
        validateEmail(customerDto.getEmail());
        validatePhoneNumber(customerDto.getPhoneNumber());
        validateUsernameByRegex(customerDto.getUserName());
        validatePreferredLanguage(customerDto.getPreferredLanguage().toString());
        validateDuplicateUsername(customerDto.getUserName());
    }

    private void validateMissingFields(CreateCustomerRequestDto customerDto) {
        if (customerDto.getUserName().isEmpty()) {
            throw new CustomerFieldMissing("Username");
        }
        if (customerDto.getFirstName().isEmpty()) {
            throw new CustomerFieldMissing("First Name");
        }
        if (customerDto.getLastName().isEmpty()) {
            throw new CustomerFieldMissing("Last Name");
        }
        if (Objects.isNull(customerDto.getEmail()) || customerDto.getEmail().isEmpty()) {
            throw new CustomerFieldMissing("Email");
        }
        if (customerDto.getPhoneNumber().isEmpty()) {
            throw new CustomerFieldMissing("Phone Number");
        }
        if (Objects.isNull(customerDto.getPreferredLanguage()) || customerDto.getPreferredLanguage().toString().isEmpty()) {
            throw new CustomerFieldMissing("Preferred Language");
        }
    }

    public boolean validateUsernameByRegex(String username) {
        if (!username.matches("^[A-Za-z][A-Za-z0-9_]{7,29}$")) {
            throw new InvalidFieldException("Invalid Username");
        }
        return Boolean.TRUE;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidFieldException("Invalid Phone Number");
        }
        return Boolean.TRUE;
    }

    public boolean validateEmail(String email) {
        if (!email.matches("[\\w+.]*@\\w+.[com|in]{2,3}")) {
            throw new InvalidFieldException("Invalid Email");
        }
        return Boolean.TRUE;
    }

    public boolean validatePreferredLanguage(String preferredLanguage) {
        if (!((preferredLanguage.equals("EN")) || (preferredLanguage.equals("DE")) || (preferredLanguage.equals("FR")))) {
            throw new InvalidFieldException("Invalid Preferred Language");
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

    public SecurityImages validateSecurityImage(CreateCustomerSecurityImageRequestDto createCustomerSecurityImageRequestDto) {
        if (Objects.isNull(createCustomerSecurityImageRequestDto.getSecurityImageCaption()) || createCustomerSecurityImageRequestDto.getSecurityImageCaption().isEmpty() || createCustomerSecurityImageRequestDto.getSecurityImageCaption().length() < 3) {
            throw new InvalidFieldException("Security Image Caption Invalid");
        }
        Optional<SecurityImages> optionalSecurityImage = securityImageRepo.findById(createCustomerSecurityImageRequestDto.getSecurityImageId());
        SecurityImages securityImage;
        if (optionalSecurityImage.isPresent()) {
            securityImage = optionalSecurityImage.get();
            return securityImage;
        }else {
            throw new NotFoundException("Security Image Not Found","Image");
        }
    }

    public void validateExistingImage(String id) {
        CustomerSecurityImages byCustomerId = customerSecurityImagesRepo.findByCustomerId(id);
        if(Objects.isNull(byCustomerId)){
            return;
        }else {
            customerSecurityImagesRepo.delete(byCustomerId);
        }
    }
}
