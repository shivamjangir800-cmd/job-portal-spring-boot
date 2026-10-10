package com.jobdata.jobdata.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.dto.RegisterRequest;
import com.jobdata.jobdata.model.user;
import com.jobdata.jobdata.service.Authservice;


import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auth")
public class authcontroller {
    private final userrepo repo;
    private final Authservice serviced;
    private final PasswordEncoder encoder;

    public authcontroller(Authservice serviced,userrepo repo,PasswordEncoder encoder){
        this.serviced = serviced;
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping("/register")
    public String getregister(Model model) {
        model.addAttribute("user" , new RegisterRequest());
        return "auth/registration";
    }
    
    @PostMapping("/register")
    public String registermethod(@ModelAttribute  RegisterRequest registerrequest ,user users,Model model) {

        if(repo.existsByEmail(users.getEmail())){
            model.addAttribute("error","email already exists");
            return "auth/registration";
        }
        serviced.userrequest(registerrequest);
        
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String getlogin(@RequestParam(value = "error",required = false) String error
                            ,@RequestParam(value = "logout",required = false) 
                            String logout,Model model) {
        
        
        if(error != null){
            model.addAttribute("error","invalid email or password");
        }

        if(logout != null){
            model.addAttribute("message","you have been logout successfully");
        }


        return "auth/login";
    }

    @GetMapping("/login-success")
    public String postlogin(Authentication authentication) {
        if(authentication.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_CANDIDATE"))){

                return "redirect:/candidate/dashboard";
            }

        if(authentication.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_RECRUITER"))){
                return "redirect:/recruiter/dashboard";
            }
        
        return "redirect:/candidate/dashboard";
    }

    @GetMapping("/forgot-password")
    public String getMethodName() {
        return "auth/forgetpass";
    }

    @PostMapping("/forgot-password")
    public String postMethod(@RequestParam("email") String email,Model model) {

     
        user users = repo.findByEmail(email)
                    .orElse(null);

        if(users == null){
            model.addAttribute("error","user not found");

            return "auth/forgetpass";
        }

        return "redirect:/auth/resetpassword?email=" + email;
    }

    @GetMapping("/resetpassword")
    public String getMethodName(@RequestParam("email") String email,Model model) {

        user users = repo.findByEmail(email)
                    .orElse(null);

        if(users == null){
            return "redirect:/forgot-password";
        }
        
        model.addAttribute("email",email);
        return "auth/resetpass";
    }

    @PostMapping("/resetpassword")
    public String postMethodName(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {

        
        if (!password.equals(confirmPassword)) { 
            model.addAttribute( "error", "Passwords do not match." ); 
            
            model.addAttribute("email", email); 
            return "resetpass";
        }
        user users = repo.findByEmail(email)
                    .orElse(null);

        if (users == null) { 
            
            return "redirect:/forgot-password"; 
        }
        users.setPassword(encoder.encode(password));
        repo.save(users);
        
        return "redirect:/login?resetSuccess=true";
    }    
}
