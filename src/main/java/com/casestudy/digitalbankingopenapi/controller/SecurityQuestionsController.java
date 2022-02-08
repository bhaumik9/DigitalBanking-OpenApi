package com.casestudy.digitalbankingopenapi.controller;

import com.casestudy.digitalbankingopenapi.service.SecurityQuestionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("customer-service")
public class SecurityQuestionsController {

    private SecurityQuestionsService securityQuestionsService;

    public SecurityQuestionsController(SecurityQuestionsService securityQuestionsService){
        this.securityQuestionsService=securityQuestionsService;
    }

    @GetMapping("/client-api/v1/securityQuestions")
    public ResponseEntity<Object> getAllSecurityQuestions(){
        return securityQuestionsService.getAllSecurityQuestions();
    }
    @PostMapping("/client-api/v1/securityQuestions")
    public ResponseEntity<Object> addQuestions(@RequestBody Map<String,String> question){
        return securityQuestionsService.addQuestion(question.get("question"));
    }
    @GetMapping("/client-api/v1/customers/{username}/securityQuestions")
    public ResponseEntity<Object> getSecurityQuestionsByUsername(@PathVariable String username){
       return securityQuestionsService.getSecurityQuestionsByUsername(username);
    }
    @PostMapping("/client-api/v1/customers/{username}/securityQuestions")
    public ResponseEntity<Object> addSecurityQuestionsByUsername(@PathVariable String username){
       return securityQuestionsService.addSecurityQuestionsByUsername(username);

    }
}
