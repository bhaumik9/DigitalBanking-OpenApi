package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.SecurityImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityImageRepo extends JpaRepository<SecurityImages,String> {

    SecurityImages findBySecurityImagesName(String name);
}
