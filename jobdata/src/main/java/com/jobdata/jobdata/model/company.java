package com.jobdata.jobdata.model;

// package com.jobdata.jobdata.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter 
public class company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private String website;

    private String industry;

    private String location;

    private Integer foundedYear;

    @ManyToOne 
    @JoinColumn(name = "candid_id")
    private user candid;

    // getters and setters
}
