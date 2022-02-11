package com.casestudy.digitalbankingopenapi.validation;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.exception.*;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import openapi.model.CreateCustomerRequestDto;
import openapi.model.InitiateOtpRequestDto;
import openapi.model.PreferredLanguageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.AnnotationUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BinaryOperator;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;

@ExtendWith(MockitoExtension.class)
class RequestValidationTest {

    @InjectMocks
    RequestValidation requestValidation;

    @Mock
    CustomerRepo customerRepo;

    @Test
    void validateInitiateOtpRequest_validateOtpRequest_returnsVoid() {
        InitiateOtpRequestDto initiateOtpRequestDto = new InitiateOtpRequestDto();
        initiateOtpRequestDto.setTemplateId(LOGIN);
        initiateOtpRequestDto.setUserName("bhaumik");
        Customer customer = new Customer();
        Mockito.when(customerRepo.findByUserName("bhaumik")).thenReturn(customer);
        requestValidation.validateInitiateOtpRequest(initiateOtpRequestDto);
    }

    @Test
    void validateInitiateOtpRequest_validateOtpRequest_returnsMandatoryFieldMissingException() {
        InitiateOtpRequestDto initiateOtpRequestDto = new InitiateOtpRequestDto();
        initiateOtpRequestDto.setTemplateId(LOGIN);
        Assertions.assertThrows(MandatoryFieldMissingException.class, () -> requestValidation.validateInitiateOtpRequest(initiateOtpRequestDto));
    }

    @Test
    void validateInitiateOtpRequest_validateOtpRequest_returnsCustomerNotFoundException() {
        InitiateOtpRequestDto initiateOtpRequestDto = new InitiateOtpRequestDto();
        initiateOtpRequestDto.setUserName("bhaumik");
        initiateOtpRequestDto.setTemplateId(LOGIN);
        Mockito.when(customerRepo.findByUserName("bhaumik")).thenReturn(null);
        Assertions.assertThrows(CustomerNotFound.class, () -> requestValidation.validateInitiateOtpRequest(initiateOtpRequestDto));
    }

    @Test
    void validateInitiateOtpRequest_validateOtpRequest_returnsInvalidFieldException() {
        InitiateOtpRequestDto initiateOtpRequestDto = new InitiateOtpRequestDto();
        initiateOtpRequestDto.setUserName("bhaumik");
        initiateOtpRequestDto.setTemplateId("demo");
        Customer customer = new Customer();
        Mockito.when(customerRepo.findByUserName("bhaumik")).thenReturn(customer);
        Assertions.assertThrows(InvalidFieldException.class, () -> requestValidation.validateInitiateOtpRequest(initiateOtpRequestDto));
    }

    @Test
    void validateCustomer_validationNullUsernameField_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik");
        customerRequestDto.setPhoneNumber("9988776655");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }

    @Test
    void validateCustomer_validationNullFirstName_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik@mob.com");
        customerRequestDto.setPhoneNumber("9988776655");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }

    @Test
    void validateCustomer_validationNullLastName_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("");
        customerRequestDto.setEmail("bhaumik@mmob.com");
        customerRequestDto.setPhoneNumber("9988776655");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }

    @Test
    void validateCustomer_validationNullPhoneNumber_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik");
        customerRequestDto.setPhoneNumber("");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }

    @Test
    void validateCustomer_validationNullPreferredLanguage_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik");
        customerRequestDto.setPhoneNumber("9988776655");
        Assertions.assertThrows(CustomerFieldMissing.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }

    @Test
    void validateCustomer_validationEmail_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik");
        customerRequestDto.setPhoneNumber("9988776655");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(InvalidFieldException.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }
    @Test
    void validateCustomer_validationPhoneNumber_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumik9");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik@mob.com");
        customerRequestDto.setPhoneNumber("776655");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(InvalidFieldException.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }
    @Test
    void validateCustomer_validationUserName_ReturnsCustomerFieldMissingException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bha");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik@mob.com");
        customerRequestDto.setPhoneNumber("7766553322");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Assertions.assertThrows(InvalidFieldException.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }
    @Test
    void validateCustomer_validationDuplicateUserame_ReturnsDuplicateUsernameException() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumikK89");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik@mob.com");
        customerRequestDto.setPhoneNumber("7766553322");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Mockito.when(customerRepo.existsByUserName(customerRequestDto.getUserName())).thenReturn(Boolean.TRUE);
        Assertions.assertThrows(DuplicateUsernameException.class, () -> requestValidation.validateCustomer(customerRequestDto));
    }
    @Test
    void validateCustomer_validationDuplicateUserame_ReturnsVoid() {
        CreateCustomerRequestDto customerRequestDto = new CreateCustomerRequestDto();
        customerRequestDto.setFirstName("bhaumik");
        customerRequestDto.setUserName("bhaumikK89");
        customerRequestDto.setLastName("chhunchha");
        customerRequestDto.setEmail("bhaumik@mob.com");
        customerRequestDto.setPhoneNumber("7766553322");
        customerRequestDto.setPreferredLanguage(PreferredLanguageDto.DE);
        Mockito.when(customerRepo.existsByUserName(customerRequestDto.getUserName())).thenReturn(Boolean.FALSE);
        requestValidation.validateCustomer(customerRequestDto);
    }

    @Test
    void validateStatus_validation_ReturnsFalse() {
        String status="";
        boolean actual = requestValidation.validateStatus(status);
        Assertions.assertEquals(Boolean.FALSE,actual);
    }
    @Test
    void validateStatus_validation_ReturnsTrue() {
        String status="ACTIVE";
        boolean actual = requestValidation.validateStatus(status);
        Assertions.assertEquals(Boolean.TRUE,actual);
    }
}