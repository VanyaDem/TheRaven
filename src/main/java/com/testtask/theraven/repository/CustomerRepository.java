package com.testtask.theraven.repository;

import com.testtask.theraven.domain.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.id = :newId WHERE c.id = :oldId")
    void updateId(@Param("oldId") Long oldId, @Param("newId") Long newId);

    Optional<Customer> findByEmail(String email);
}
