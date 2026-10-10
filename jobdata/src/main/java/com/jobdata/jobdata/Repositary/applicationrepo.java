package com.jobdata.jobdata.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdata.jobdata.model.Application;
import com.jobdata.jobdata.model.job;
import com.jobdata.jobdata.model.user;

import java.util.List;
import java.util.Optional;


@Repository 
public interface applicationrepo extends JpaRepository<Application,Long>{

    List<Application> findByCandidate(user candidate);
    List<Application> findByJobs(job jobes);
}