package com.jobdata.jobdata.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdata.jobdata.model.company;
import com.jobdata.jobdata.model.user;

@Repository 
public interface companyrepo extends JpaRepository<company,Long> {

    List<company> findBycandid(user users);
}



