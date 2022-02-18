package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.dto.AgeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Component
public class ExternalController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/get-age/{username}")
    public int getExternalApiData(@PathVariable String username){
        ResponseEntity<AgeResponseDto> exchange = restTemplate.exchange("https://api.agify.io/?name=" + username + "", HttpMethod.GET, null, AgeResponseDto.class);
        return exchange.getBody().getAge();
    }
}
