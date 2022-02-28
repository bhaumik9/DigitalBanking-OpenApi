package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.dto.CustomerDto;
import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.enums.CustomerStatus;
import com.casestudy.digitalbankingopenapi.exception.CustomerNotFound;
import com.casestudy.digitalbankingopenapi.exception.InvalidFieldException;
import com.casestudy.digitalbankingopenapi.exception.MandatoryFieldMissingException;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.util.Util;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomerRepo customerRepo;
    @Mock
    RequestValidation requestValidation;
    @Mock
    Util util;

    @Test
    void generateOtp_GeneratingRandomNumber_ReturnsString() {
        String expected = "999999";
        Mockito.when(util.getSecureRandom()).thenReturn(999999);
        String actual = customerService.generateOtp();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add() {
        CreateCustomerRequestDto customerDto = new CreateCustomerRequestDto();
        customerDto.setUserName("bhaumik9");
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhunchha");
        customerDto.setPhoneNumber("9999999999");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Customer customer = new Customer();
        customer.setUserName("bhaumik9");
        customer.setFirstName("bhaumik");
        customer.setLastName("chhunchha");
        customer.setPhoneNumber("9999999999");
        customer.setEmail("bhaumik@mob.com");
        customer.setPreferredLanguage(PreferredLanguageDto.DE.toString());
        customer.setStatus(CustomerStatus.ACTIVE.toString());
        customer.setExternalId(customer.getUserName() + "_ext");
        customer.setCreatedBy(System.getProperty("user.name"));
        customer.setUpdatedBy(System.getProperty("user.name"));
        customer.setCreatedOn(LocalDateTime.now());
        customer.setUpdatedOn(LocalDateTime.now());
        Customer savedCustomer = new Customer();
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);
        CreateCustomerResponseDto actual = customerService.add(customerDto);
        CreateCustomerResponseDto expectedID = new CreateCustomerResponseDto();
        Assertions.assertEquals(expectedID, actual);
    }

    @Test
    void update_UpdatingCustomer_ReturnsVoid() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = "bhaumik9";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validatePhoneNumber(customerDto.getPhoneNumber(),"update customer")).thenReturn(Boolean.TRUE);
        Mockito.when(requestValidation.validateEmail(customerDto.getEmail(),"update customer")).thenReturn(Boolean.TRUE);
        customerService.update(customerDto, username);
        Mockito.verify(customerRepo).save(customer);

    }

    @Test
    void update_UpdatingCustomer_ReturnsMandatoryFieldMissingException() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = null;
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenThrow(MandatoryFieldMissingException.class);
        Assertions.assertThrows(MandatoryFieldMissingException.class, () -> customerService.update(customerDto, username));
    }

    @Test
    void update_UpdatingCustomer_ReturnsCustomerNotFoundException() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = "demo";
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenThrow(CustomerNotFound.class);
        Assertions.assertThrows(CustomerNotFound.class, () -> customerService.update(customerDto, username));
    }

    @Test
    void update_UpdatingCustomerPhoneNumber_ReturnsInvalidFieldException() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = "demo";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validatePhoneNumber(customerDto.getPhoneNumber(),"update customer")).thenThrow(InvalidFieldException.class);
        Assertions.assertThrows(InvalidFieldException.class, () -> customerService.update(customerDto, username));
    }

    @Test
    void update_UpdatingCustomerEmail_ReturnsInvalidFieldException() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = "demo";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validateEmail(customerDto.getEmail(),"update customer")).thenThrow(InvalidFieldException.class);
        Assertions.assertThrows(InvalidFieldException.class, () -> customerService.update(customerDto, username));
    }

    @Test
    void update_UpdatingCustomerPreferredLanguage_ReturnsInvalidFieldException() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        String username = "demo";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validatePreferredLanguage(customerDto.getPreferredLanguage().toString(),"update customer")).thenThrow(InvalidFieldException.class);
        Assertions.assertThrows(InvalidFieldException.class, () -> customerService.update(customerDto, username));
    }

    @Test
    void update_UpdatingCustomerStatusTrue_ReturnsVoid() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        customerDto.setStatus(StatusDto.ACTIVE);
        String username = "demo";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validateStatus(customerDto.getStatus().toString())).thenReturn(Boolean.TRUE);
        customerService.update(customerDto, username);
        Mockito.verify(customerRepo).save(customer);
    }

    @Test
    void update_UpdatingCustomerStatusFalse_ReturnsVoid() {
        PatchCustomerRequestDto customerDto = new PatchCustomerRequestDto();
        customerDto.setFirstName("bhaumik");
        customerDto.setLastName("chhuchha");
        customerDto.setEmail("bhaumik@mob.com");
        customerDto.setPhoneNumber("9999888877");
        customerDto.setPreferredLanguage(PreferredLanguageDto.EN);
        customerDto.setStatus(StatusDto.ACTIVE);
        String username = "demo";
        Customer customer = new Customer();
        Mockito.when(requestValidation.validateUserNameInDatabase(username, "update")).thenReturn(customer);
        Mockito.when(requestValidation.validateStatus(customerDto.getStatus().toString())).thenReturn(Boolean.TRUE);
        customerService.update(customerDto, username);
        Mockito.verify(customerRepo).save(customer);
    }
}