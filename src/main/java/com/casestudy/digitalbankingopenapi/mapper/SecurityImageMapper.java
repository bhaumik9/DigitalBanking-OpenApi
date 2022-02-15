package com.casestudy.digitalbankingopenapi.mapper;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityImages;
import openapi.model.GetCustomerSecurityImageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SecurityImageMapper {

    @Mapping(source = "customerSecurityImages.securityImages.securityImagesName",target = "securityImageName")
    @Mapping(source = "customerSecurityImages.securityImages.securityImageUrl",target = "securityImageUrl")
    @Mapping(source = "customerSecurityImages.customerSecurityImagesId.securityImageId",target = "securityImageId")
    GetCustomerSecurityImageResponseDto entityToCustomerSecurityImageResponseDto(CustomerSecurityImages customerSecurityImages);
}
