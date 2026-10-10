package com.jobdata.jobdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
public class user {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank 
    private String name;

    @Email 
    @NotBlank 
    @Column(unique = true,nullable = false)
    private String email;
    
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String department;
}