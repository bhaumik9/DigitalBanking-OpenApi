package com.casestudy.digitalbankingopenapi.validation;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.exception.*;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.InitiateOtpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;
import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.REGISTRATION;

@Component
public class RequestValidation {

    private CustomerRepo customerRepo;

    @Autowired
    public RequestValidation(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void validateInitiateOtpRequest(InitiateOtpRequestDto customerOtpDto) {
        validateUserNameInDatabase(customerOtpDto.getUserName(),"otp");
        isValidTemplateId(customerOtpDto.getTemplateId());
    }
    public Customer validateUserNameInDatabase(String userName, String type) {
        if (Objects.isNull(userName)) {
            throw new MandatoryFieldMissingException(type);
        }
        Customer customer = customerRepo.findByUserName(userName);
        if (Objects.isNull(customer)) {
            throw new CustomerNotFound(type);
        }
        return customer;
    }

    public void isValidTemplateId(String templateId) {
        if (Objects.isNull(templateId) || templateId.isEmpty() || templateId.equals(REGISTRATION) || templateId.equals(LOGIN)) {
            return;
        }
        throw new InvalidTemplateIdException();
    }

    public void validateCustomer(CreateCustomerRequestDto customerDto) {
        validateUsernameInDatabase(customerDto.getUserName());
    }

    public boolean validatePhoneNumber(String phoneNumber){
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidFieldException("Invalid Phone Number");
        }
        return Boolean.TRUE;
    }

    public boolean validateEmail(String email){
        if (!email.matches("[\\w+.]*@\\w+.[com|in]{2,3}")) {
            throw new InvalidFieldException("Invalid Email");
        }
        return Boolean.TRUE;
    }

    public boolean validatePreferredLanguage(String preferredLanguage){
        if (!((preferredLanguage.equals("EN")) || (preferredLanguage.equals("DE")) || (preferredLanguage.equals("FR")))) {
            throw new InvalidFieldException("Invalid Preferred Language");
        }
        return Boolean.TRUE;
    }

    public boolean validateStatus(String status){
        if(status.equals("ACTIVE") || status.equals("INACTIVE") || status.equals("PENDING")){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void validateUsernameInDatabase(String username) {
        if (customerRepo.existsByUserName(username)) {
            throw new DuplicateUsernameException();
        }
    }
}
