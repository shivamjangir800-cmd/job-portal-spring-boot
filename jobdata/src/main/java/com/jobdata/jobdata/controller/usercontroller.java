package com.jobdata.jobdata.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.model.user;
import org.springframework.web.bind.annotation.PostMapping;


@Controller 
public class usercontroller {

    private final userrepo repo;

    public usercontroller(userrepo repo){
        this.repo = repo;
    }

    @GetMapping("/")
    public String getuder() {
        return "index";
    }
    
    @GetMapping("/new")
    public String addempl(Model model) {
        model.addAttribute("employee",new user());
        return "addempl";
    }
    
    @GetMapping("/employees")
    public String viewempl(Model model) {

        List<user> use = repo.findAll();
        model.addAttribute("employees",use);
        return "viewempl";
    }

    @GetMapping("/employees/manage")
    public String manageempl() {
        return "manageempl";
    }

    @PostMapping("/save")
    public String savemethod(user User) {

        repo.save(User);
        return "redirect:/employees";
    }

    @GetMapping("/jobs")
    public String getjobs() {
        return "candidate/jobs";
    }
       
}
