package com.casestudy.digitalbankingopenapi.repository;

import com.casestudy.digitalbankingopenapi.entity.CustomerSecurityImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSecurityImagesRepo extends JpaRepository<CustomerSecurityImages,String> {
    CustomerSecurityImages findByCustomerId(String customerId);
}
