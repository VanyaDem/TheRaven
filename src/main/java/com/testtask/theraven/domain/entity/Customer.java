package com.testtask.theraven.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column
    private Long id;

    @Column
    private Long created;

    @Column
    private Long updated;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 14)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
