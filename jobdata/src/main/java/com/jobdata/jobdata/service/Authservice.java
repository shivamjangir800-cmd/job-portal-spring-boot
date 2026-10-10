package com.jobdata.jobdata.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.dto.RegisterRequest;
import com.jobdata.jobdata.model.user;

@Service 
public class Authservice {

    private final userrepo repo;
    private final PasswordEncoder encoder;

    public Authservice(userrepo repo,PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    public String userrequest(RegisterRequest req){

        if(repo.existsByEmail(req.getEmail())){
            return "user already registered";
        }
        user use = new user();
        use.setName(req.getName());
        use.setEmail(req.getEmail());
        use.setPassword(encoder.encode(req.getPassword()));

        use.setDepartment(req.getDepartment());

        repo.save(use);
        return "Registered successfuly";
    }

}
