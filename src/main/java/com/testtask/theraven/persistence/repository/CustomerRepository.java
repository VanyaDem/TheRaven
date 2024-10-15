package com.testtask.theraven.persistence.repository;

import com.testtask.theraven.domain.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
