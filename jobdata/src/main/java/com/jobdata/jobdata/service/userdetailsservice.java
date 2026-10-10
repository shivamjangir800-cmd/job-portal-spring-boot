package com.jobdata.jobdata.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.model.user;

@Service 
public class userdetailsservice implements UserDetailsService{

    private final userrepo repo;
    
    public userdetailsservice(userrepo repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        user users = repo.findByEmail(email)
                    .orElseThrow(() -> 
                                new UsernameNotFoundException(
                                    "user not found with email"
                                ));

        return new org.springframework.security.core.userdetails.User(
                users.getEmail(),
                users.getPassword(),
                Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_" + users.getDepartment())
                )
        );
    }
}
