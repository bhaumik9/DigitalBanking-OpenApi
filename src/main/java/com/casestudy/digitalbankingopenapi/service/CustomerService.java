package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.dto.CustomerDto;
import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.enums.CustomerStatus;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.util.Util;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.CreateCustomerResponseDto;
import openapi.model.PatchCustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    private RequestValidation requestValidation;

    Util util=new Util();

    public String generateOtp() {
        int randomValue = util.getSecureRandom();
        return String.valueOf(randomValue);
    }

    public CreateCustomerResponseDto add(CreateCustomerRequestDto customerDto) {
        requestValidation.validateCustomer(customerDto);
        Customer customer=new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUserName(customerDto.getUserName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setPreferredLanguage(customerDto.getPreferredLanguage().toString());
        customer.setStatus(CustomerStatus.ACTIVE.toString());
        customer.setExternalId(customer.getUserName() + "_ext");
        customer.setCreatedBy(System.getProperty("user.name"));
        customer.setUpdatedBy(System.getProperty("user.name"));
        customer.setCreatedOn(LocalDateTime.now());
        customer.setUpdatedOn(LocalDateTime.now());
        return insertIntoDatabase(customer);
    }

    private CreateCustomerResponseDto insertIntoDatabase(Customer customer) {
        CreateCustomerResponseDto createCustomerResponseDto=new CreateCustomerResponseDto();
        Customer savedCustomer = customerRepo.save(customer);
        createCustomerResponseDto.setId(savedCustomer.getId());
        return createCustomerResponseDto;
    }

    public void update(PatchCustomerRequestDto customerDto, String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "update");
        if (!(Objects.isNull(customerDto.getFirstName()) || customerDto.getFirstName().isEmpty())) {
            customer.setFirstName(customerDto.getFirstName());
        }
        if (!(Objects.isNull(customerDto.getLastName()) || customerDto.getLastName().isEmpty())) {
            customer.setLastName(customerDto.getLastName());
        }
        if ((!(Objects.isNull(customerDto.getPhoneNumber()) || customerDto.getPhoneNumber().isEmpty())) && requestValidation.validatePhoneNumber(customerDto.getPhoneNumber())) {
                customer.setPhoneNumber(customerDto.getPhoneNumber());
        }
        if ((!(Objects.isNull(customerDto.getEmail()) || customerDto.getEmail().isEmpty())) && requestValidation.validateEmail(customerDto.getEmail())) {
                customer.setEmail(customerDto.getEmail());
        }
        if ((!(Objects.isNull(customerDto.getPreferredLanguage()) || customerDto.getPreferredLanguage().toString().isEmpty())) && requestValidation.validatePreferredLanguage(customerDto.getPreferredLanguage().toString())) {
                customer.setPreferredLanguage(customerDto.getPreferredLanguage().toString());
        }
        if ((!(Objects.isNull(customerDto.getStatus()) || customerDto.getStatus().toString().isEmpty())) && requestValidation.validateStatus(customerDto.getStatus().toString())) {
                customer.setStatus(customerDto.getStatus().toString());
        }
        customerRepo.save(customer);
    }
}
