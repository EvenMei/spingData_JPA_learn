package com.meiyukai.dao;

import com.meiyukai.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "customerDao")
public interface CustomerDao extends JpaRepository<Customer , Long> , JpaSpecificationExecutor<Customer> {

}
