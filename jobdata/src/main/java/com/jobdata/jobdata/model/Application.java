package com.jobdata.jobdata.model;

import jakarta.persistence.Column;
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
public class Application {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "candidate_id")
    private user candidate;

    @ManyToOne 
    @JoinColumn(name = "job_id")
    private job jobs;

    private String resumeurl;

    @Column(length = 2000)
    private String coverletter;

    private String status;

    public Application(){

    }
}
