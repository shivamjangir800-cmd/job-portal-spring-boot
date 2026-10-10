package com.jobdata.jobdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
public class job {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private Double salary;

    private String experience;

    private String skills;

    private String status;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private user recruiter;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private company company;

    public job(){

    }

}
