package com.casestudy.digitalbankingopenapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerOtpDto {

    String userName;
    String templateId;
}
