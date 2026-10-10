package com.jobdata.jobdata.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jobdata.jobdata.Repositary.companyrepo;
import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.model.company;
import com.jobdata.jobdata.model.user;

import org.springframework.web.bind.annotation.PostMapping;



@Controller 
@RequestMapping("recruiter/company")
public class companycontroller {

    private final companyrepo repo;
    private final userrepo rp;

    public companycontroller(companyrepo repo,userrepo rp){
        this.repo = repo;
        this.rp = rp;
    }
    @GetMapping("/create")
    public String getMethodName(Model model) {
        model.addAttribute("company",new company());
        return "recruiter/companymanage";
    }

    @PostMapping("/save")
    public String postMethodName(company company,Authentication authentication) {

        user recruiter = rp.findByEmail(authentication.getName())
                        .orElse(null);
        company.setCandid(recruiter);
        repo.save(company);
        return "redirect:/recruiter/company";
    }

    @GetMapping("/profile/{id}")
    public String postMethodName(@PathVariable Long id,Model model) {
        
        company comp = repo.findById(id)
                        .orElseThrow(() -> 
                                new RuntimeException("company not found"));
        
        model.addAttribute("company",comp);
        return "recruiter/companydeatails";
    }

    @GetMapping
    public String postMethodName(Authentication Authentication,Model model) {

        
        user users = rp.findByEmail(Authentication.getName())
                    .orElse(null);
        System.out.println("user is " + users);
        List<company> comp = repo.findBycandid(users);

        if (comp == null) {
            
        return "redirect:/recruiter/company/create";
    }
        model.addAttribute("companies", comp);

        return "recruiter/companyprofiles";
    }

    @GetMapping("/edit/{id}")
    public String getMethodName(@PathVariable Long id,Model model) {

        company companys = repo.findById(id)
                        .orElse(null);

        model.addAttribute("company",companys);
        return "recruiter/editcompany";
    }

    @PostMapping("/update")
    public String postMethodName(company comp) {
        
        repo.save(comp);
        return "redirect:/recruiter/company/profile/" + comp.getId();
    }
    
    
}
