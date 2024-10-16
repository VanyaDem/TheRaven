package com.testtask.theraven.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer entity for the "customer" table in the database.
 * This class maps to the corresponding columns and provides essential information
 * such as the customer's full name, email, phone number, and status (active or not).
 *
 * @author Ivan Demydenko
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "customer_id_seq", allocationSize = 1)
    @Column
    private Long id;

    @Column
    private Long created;

    @Column
    private Long updated;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, length = 100, unique = true,updatable = false)
    private String email;

    @Column(length = 14)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
