package com.jobdata.jobdata.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdata.jobdata.model.user;

@Repository 
public interface userrepo extends JpaRepository<user,Long>{

    public boolean existsByEmail(String email);
    Optional<user> findByEmail(String email);
}
