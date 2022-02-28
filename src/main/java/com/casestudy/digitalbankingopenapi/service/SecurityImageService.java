package com.casestudy.digitalbankingopenapi.service;

import com.casestudy.digitalbankingopenapi.entity.Customer;
import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityImages;
import com.casestudy.digitalbankingopenapi.entity.SecurityImages;
import com.casestudy.digitalbankingopenapi.exception.NotFoundException;
import com.casestudy.digitalbankingopenapi.mapper.SecurityImageMapperImpl;
import com.casestudy.digitalbankingopenapi.repository.CustomerRepo;
import com.casestudy.digitalbankingopenapi.repository.CustomerSecurityImagesRepo;
import com.casestudy.digitalbankingopenapi.validation.RequestValidation;
import openapi.model.CreateCustomerSecurityImageRequestDto;
import openapi.model.GetCustomerSecurityImageResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@Service
public class SecurityImageService {

    private SecurityImageMapperImpl securityImageMapper;
    RequestValidation requestValidation;
    private CustomerSecurityImagesRepo customerSecurityImagesRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public SecurityImageService(SecurityImageMapperImpl securityImageMapper, RequestValidation requestValidation, CustomerSecurityImagesRepo customerSecurityImagesRepo, CustomerRepo customerRepo) {
        this.securityImageMapper = securityImageMapper;
        this.requestValidation = requestValidation;
        this.customerSecurityImagesRepo = customerSecurityImagesRepo;
        this.customerRepo = customerRepo;
    }

    public ResponseEntity<Void> addImageByUsername(String username, CreateCustomerSecurityImageRequestDto createCustomerSecurityImageRequestDto) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "customerSecurityImage");
        SecurityImages securityImage = requestValidation.validateSecurityImage(createCustomerSecurityImageRequestDto,"customerSecurityImage");
        requestValidation.validateExistingImage(customer.getId(),customer);
        CustomerSecurityImages customerSecurityImages = new CustomerSecurityImages();
        customerSecurityImages.setCustomer(customer);
        customerSecurityImages.setSecurityImages(securityImage);
        customerSecurityImages.setSecurityImageCaption(createCustomerSecurityImageRequestDto.getSecurityImageCaption());
        customerSecurityImages.setCreatedOn(LocalDateTime.now());
//        CustomerSecurityImages save = customerSecurityImagesRepo.save(customerSecurityImages);
//        customer.setCustomerSecurityImage(save);
//        customerRepo.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<GetCustomerSecurityImageResponseDto> getImageByUsername(String username) {
        Customer customer = requestValidation.validateUserNameInDatabase(username, "getCustomerSecurityImage");
        CustomerSecurityImages customerSecurityImage = customer.getCustomerSecurityImage();
        if(Objects.isNull(customerSecurityImage) || Objects.isNull(customerSecurityImage.getSecurityImages())){
            throw new NotFoundException("There is no image for requested user","getSecurityImage");
        }
        GetCustomerSecurityImageResponseDto securityImageResponseDto = securityImageMapper.entityToCustomerSecurityImageResponseDto(customerSecurityImage);
        return new ResponseEntity<>(securityImageResponseDto, HttpStatus.OK);
    }
}
