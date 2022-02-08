package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.dto.CustomerDto;
import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.enums.CustomerStatus;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.util.Util;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
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

    public String add(CustomerDto customerDto) {
        Customer customer = customerDto.toEntity();
        customer.setStatus(CustomerStatus.ACTIVE.toString());
        customer.setExternalId(customer.getUserName() + "_ext");
        customer.setCreatedBy(System.getProperty("user.name"));
        customer.setUpdatedBy(System.getProperty("user.name"));
        customer.setCreatedOn(LocalDateTime.now());
        customer.setUpdatedOn(LocalDateTime.now());
        return insertIntoDatabase(customer);
    }

    private String insertIntoDatabase(Customer customer) {
        Customer savedCustomer = customerRepo.save(customer);
        return savedCustomer.getId();
    }

    public void update(CustomerDto customerDto, String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "update");
        if (!(Objects.isNull(customerDto.getFirstName()) || customerDto.getFirstName().isEmpty())) {
            customer.setFirstName(customerDto.getFirstName());
        }
        if (!(Objects.isNull(customerDto.getLastName()) || customerDto.getLastName().isEmpty())) {
            customer.setLastName(customerDto.getLastName());
        }
        if (!(Objects.isNull(customerDto.getPhoneNumber()) || customerDto.getPhoneNumber().isEmpty())) {
            if (requestValidation.validatePhoneNumber(customerDto.getPhoneNumber())) {
                customer.setPhoneNumber(customerDto.getPhoneNumber());
            }
        }
        if (!(Objects.isNull(customerDto.getEmail()) || customerDto.getEmail().isEmpty())) {
            if (requestValidation.validateEmail(customerDto.getEmail())) {
                customer.setEmail(customerDto.getEmail());
            }
        }
        if (!(Objects.isNull(customerDto.getPreferredLanguage()) || customerDto.getPreferredLanguage().isEmpty())) {
            if (requestValidation.validatePreferredLanguage(customerDto.getPreferredLanguage())) {
                customer.setPreferredLanguage(customerDto.getPreferredLanguage());
            }
        }
        if (!(Objects.isNull(customerDto.getStatus()) || customerDto.getStatus().isEmpty())) {
            if (requestValidation.validateStatus(customerDto.getStatus())) {
                customer.setStatus(customerDto.getStatus());
            }
        }
        customerRepo.save(customer);
    }
}
