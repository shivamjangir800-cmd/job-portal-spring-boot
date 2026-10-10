package com.jobdata.jobdata.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobdata.jobdata.model.job;
import com.jobdata.jobdata.model.user;

@Repository 
public interface jobrepo extends JpaRepository<job,Long> {

    List<job> findByRecruiter(user recruiter);    

    @Query(""" 
        SELECT j FROM job j WHERE j.status = 'OPEN' 
        AND ( :keyword IS NULL 
            OR :keyword = '' 
            OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) 
            OR LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) 
            OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) ) 
            AND ( :location IS NULL 
                OR :location = '' 
                OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) 
                ) 
            """)

            List<job> searchJobs(
                @Param("keyword") String keyword, 
                @Param("location") String location
            );

}
