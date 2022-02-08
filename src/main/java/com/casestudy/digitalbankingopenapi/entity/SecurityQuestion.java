package com.casestudy.digitalbankingopenapi.entity;

import com.casestudy.digitalbankingopenapi.dto.SecurityQuestionsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class SecurityQuestion {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
//    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Column(name = "security_question_text")
    private String securityQuestionText;

    @OneToMany(mappedBy = "securityQuestion")
    List<CustomerSecurityQuestions> customerSecurityQuestions = new ArrayList<>();

    public SecurityQuestion(String question) {
        this.securityQuestionText=question;
    }

    public SecurityQuestion() {

    }

    public SecurityQuestionsDto toDto(){
        return new SecurityQuestionsDto(id,securityQuestionText);
    }
}
