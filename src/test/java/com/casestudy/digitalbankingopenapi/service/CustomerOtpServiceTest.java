package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerOtp;
import com.casestudy.digitalbankingopenapi.repository.CustomerOtpRepo;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import openapi.model.InitiateOtpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Comparator;

import static com.casestudy.digitalbankingopenapi.constants.ErrorCode.LOGIN;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CustomerOtpServiceTest {

    @InjectMocks
    CustomerOtpService customerOtpService;
    @Mock
    CustomerOtpRepo customerOtpRepo;
    @Mock
    CustomerRepo customerRepo;
    @Mock
    CustomerService customerService;

    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(customerOtpService,"messageLogin","Your otp for Login is");
        ReflectionTestUtils.setField(customerOtpService,"messageDefault","Your otp is");
        ReflectionTestUtils.setField(customerOtpService,"messageRegistration","Your otp for Registration is");
    }

    @Test
    void getData_fetchingCustomerOtpData_ReturnsCustomOtp() {

        InitiateOtpRequestDto customerOtpDto = new InitiateOtpRequestDto();
        customerOtpDto.setTemplateId("LOGIN");
        customerOtpDto.setUserName("bhaumik");
        Mockito.when(customerService.generateOtp()).thenReturn("123456");
        CustomerOtp expectedCustomerOtp = new CustomerOtp();
        expectedCustomerOtp.setOtpMessage("Your otp for Login is 123456");
        expectedCustomerOtp.setOtp("123456");
        expectedCustomerOtp.setRetries(0);
        expectedCustomerOtp.setCreatedOn(LocalDateTime.now());
        expectedCustomerOtp.setExpiresOn(LocalDateTime.now().plusMinutes(5));
        expectedCustomerOtp.setUserName(customerOtpDto.getUserName());
        CustomerOtp actualCustomerOtp = customerOtpService.getData(customerOtpDto);

        Comparator<CustomerOtp> customerOtpComparator = new Comparator<CustomerOtp>() {
            @Override
            public int compare(CustomerOtp o1, CustomerOtp o2) {

                System.out.println("o1 message="+o1.getOtpMessage());
                System.out.println("o2 message="+o2.getOtpMessage());

                if (o1.getUserName().equals(o2.getUserName()) &&
                        (o1.getOtp().equals(o2.getOtp())) &&
                        (o1.getOtpMessage().equals(o2.getOtpMessage())) &&
                        o1.getRetries()==o2.getRetries()&&
                        o1.getCreatedOn().getClass().equals(o2.getCreatedOn().getClass()) &&
                        (o1.getExpiresOn().getClass().equals(o2.getExpiresOn().getClass())) &&
                        o1.getTemplateId() == (o2.getTemplateId())) {
                    return 0;
                }
                return -1;
            }
        };
        assertThat(expectedCustomerOtp).usingComparator(customerOtpComparator).isEqualTo(actualCustomerOtp);
    }

    @Test
    void update_updatesCustomerOtpData_ReturnVoid() {

        CustomerOtp customerOtp = new CustomerOtp();
        customerOtp.setUserName("bhaumik");
        customerOtp.setTemplateId(LOGIN);
        customerOtp.setOtp("123456");
        customerOtp.setOtpMessage("OTP for the LOGIN is 123456");
        customerOtp.setCreatedOn(LocalDateTime.now());
        customerOtp.setExpiresOn(LocalDateTime.now().plusMinutes(5));
        customerOtp.setRetries(0);
        Customer customer = Mockito.mock(Customer.class);
        CustomerOtp customerOtpDb = new CustomerOtp();
        Mockito.when(customerRepo.findByUserName(customerOtp.getUserName())).thenReturn(customer);
        Mockito.when(customer.getCustomerOtp()).thenReturn(customerOtpDb);
        customerOtpService.update(customerOtp);
        Mockito.verify(customerOtpRepo, Mockito.times(1)).save(customerOtpDb);
    }

    @Test
    void update_insertNewCustomerOtpData_ReturnVoid() {

        CustomerOtp customerOtpDto = new CustomerOtp();
        customerOtpDto.setUserName("bhaumik");
        customerOtpDto.setTemplateId("LOGIN");
        customerOtpDto.setOtp("123456");
        customerOtpDto.setOtpMessage("OTP for the LOGIN is 123456");
        customerOtpDto.setCreatedOn(LocalDateTime.now());
        customerOtpDto.setExpiresOn(LocalDateTime.now().plusMinutes(5));
        customerOtpDto.setRetries(0);

        Customer customer = Mockito.mock(Customer.class);
        CustomerOtp customerOtpDb = null;
        Mockito.when(customerRepo.findByUserName(customerOtpDto.getUserName())).thenReturn(customer);
        Mockito.when(customer.getCustomerOtp()).thenReturn(customerOtpDb);
        customerOtpService.update(customerOtpDto);
        Mockito.verify(customerOtpRepo, Mockito.times(1)).save(Mockito.any(CustomerOtp.class));

    }
}