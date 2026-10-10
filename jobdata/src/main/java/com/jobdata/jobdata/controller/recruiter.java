package com.jobdata.jobdata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobdata.jobdata.Repositary.applicationrepo;
import com.jobdata.jobdata.Repositary.jobrepo;
import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.model.Application;
import com.jobdata.jobdata.model.job;
import com.jobdata.jobdata.model.user;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
@RequestMapping("/recruiter")
public class recruiter {

    private final jobrepo repo;
    private final userrepo userrepo;
    private final applicationrepo apprepo;

    public recruiter(jobrepo repo,userrepo userrepo,applicationrepo apprepo){
        this.repo = repo;
        this.userrepo = userrepo;
        this.apprepo = apprepo;
    }

    @GetMapping("/dashboard")
    public String getMethodName(Authentication authentication,Model model) {

        user recruiter = userrepo.findByEmail(authentication.getName())
                        .orElseThrow(() -> 
                            new RuntimeException("recruiter not found"));

        
        List<job> jobs = repo.findByRecruiter(recruiter);

        Map<Long,Integer> applicationcount = new HashMap<>();

        int totalapplication = 0;
        int acceptedapplication = 0;

        for(job jobe : jobs){

            List<Application> applicationn = apprepo.findByJobs(jobe);

            applicationcount.put(jobe.getId(),applicationn.size());

            totalapplication += applicationn.size();

            for(Application appli : applicationn){

                if(appli.getStatus() != null && 
                        appli.getStatus().equalsIgnoreCase("ACCEPTED")){

                        acceptedapplication++;
                        }
            }                         
        }

        long activejobs = jobs.stream()
                                .filter(job -> 
                                        job.getStatus() != null
                                        && job.getStatus()
                                        .equalsIgnoreCase("ACTIVE"))
                                .count();

        model.addAttribute("user",recruiter);
        model.addAttribute("jobs",jobs);

        model.addAttribute("applicationCounts",applicationcount);
        model.addAttribute("totalJobs",jobs.size());
        model.addAttribute("activeJobs",activejobs);
        model.addAttribute("totalApplictions",totalapplication);
        model.addAttribute("acceptedApplicatons",acceptedapplication);
        return "recruiter/dashboard";
    }

    @GetMapping("/profile")
    public String getprofile(Authentication authentication,Model model) {
        user recruiter = userrepo.findByEmail(authentication.getName())
                        .orElseThrow(() -> 
                                    new RuntimeException("user not found"));
        List<job> jobes = repo.findByRecruiter(recruiter);
        int totalapplication = 0;
        int candidatehired = 0;
        for(job jobe : jobes){

            List<Application> applications = apprepo.findByJobs(jobe);

            totalapplication += applications.size();

            for(Application appli : applications){
                if(appli.getStatus() != null &&
                    appli.getStatus().equalsIgnoreCase("ACCEPTED")){
                        candidatehired++;
                    }

                
            }
            
        }

         long activejobs = jobes.stream()
                                .filter(job -> 
                                        job.getStatus() != null
                                        && job.getStatus()
                                        .equalsIgnoreCase("ACTIVE"))
                                .count();
        
        model.addAttribute("jobsposted",jobes.size());
        model.addAttribute("totalApplications",totalapplication);
        model.addAttribute("candidatehired",candidatehired);
        model.addAttribute("activejobs",activejobs);

       
        model.addAttribute("user",recruiter);
        return "recruiter/profile";
    }


    @GetMapping("/applicants")
    public String getMethod(Authentication authentication,Model model) {

        user recruiter = userrepo.findByEmail(authentication.getName())
                            .orElseThrow(() -> 
                                    new RuntimeException("recruiter not found"));

        List<job> jobs = repo.findByRecruiter(recruiter);

        List<Application> allapplication = new ArrayList<>();
        for(job jobes:jobs){

            List<Application> applic = apprepo.findByJobs(jobes);

            allapplication.addAll(applic);
        }
        model.addAttribute("applications",allapplication);
        return "recruiter/applicants";
    }

    @PostMapping("/applications/{id}/accept")
    public String postMethodName(@PathVariable Long id) {
        
        Application applic = apprepo.findById(id)
                            .orElseThrow(() -> 
                                    new RuntimeException("applicatoio not found"));

        applic.setStatus("ACCEPTED");
        apprepo.save(applic);
        return "redirect:/recruiter/applicants";
    }

    @PostMapping("/applications/{id}/reject")
    public String postMethod(@PathVariable Long id) {
        
        Application application = apprepo.findById(id)
                                    .orElseThrow(() -> 
                                            new RuntimeException("application not found"));
        
        application.setStatus("REJECT");
        apprepo.save(application);
        return "redirect:/recruiter/applicants";
    }

    @GetMapping("/jobs")
    public String getjobs(Authentication authentication,Model model) {

        user recruiter = userrepo.findByEmail(authentication.getName())
                            .orElseThrow(() -> 
                                    new RuntimeException("recruier not found"));

        
        List<job> jobs = repo.findByRecruiter(recruiter);
        Map<Long,Integer> totalapplication = new HashMap<>();

        for(job jobes : jobs){

            List<Application> application = apprepo.findByJobs(jobes);

            totalapplication.put(jobes.getId(),application.size());

        }

        model.addAttribute("applicationCounts",totalapplication);
        model.addAttribute("jobs",jobs);
        return "recruiter/my_job";
    }

    @GetMapping("/jobs/edit/{id}")
    public String getedit(@PathVariable Long id,Model model) {

        job jobes = repo.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("jobes not found"));
        model.addAttribute("job",jobes);
        return "recruiter/edit";
    }

    @PostMapping("/jobs/update")
    public String postMethodName(@ModelAttribute job jobs) {

        job jobes = repo.findById(jobs.getId())
                    .orElseThrow(() -> 
                            new RuntimeException("job not found"));

        jobes.setTitle(jobs.getTitle());
        jobes.setDescription(jobs.getDescription());
        jobes.setLocation(jobs.getLocation());
        jobes.setSalary(jobs.getSalary());
        jobes.setExperience(jobs.getExperience());
        jobes.setSkills(jobs.getSkills());
        jobes.setStatus(jobs.getStatus());
        repo.save(jobes);
        return "redirect:/recruiter/jobs";
    }
    
    

    @GetMapping("/jobs/create")
    public String getcreate(Model model) {

        model.addAttribute("job",new job());
        return "recruiter/createjob";
    }

    @PostMapping("/jobs/save")
    public String postMethodName(@ModelAttribute job jobs,Authentication authentication){

        user recruiters = userrepo.findByEmail(authentication.getName())
                            .orElseThrow(() -> 
                                    new RuntimeException("recruiters not found"));

        jobs.setRecruiter(recruiters);

        if(jobs.getStatus() == null ||
                jobs.getStatus().isBlank()){

                    jobs.setStatus("ACTIVE");
                }

        repo.save(jobs);
        
        return "redirect:/recruiter/jobs";
    }

    @GetMapping("/profile/edit")
    public String getedit(Authentication authentication,Model model) {

        user users = userrepo.findByEmail(authentication.getName())
                    .orElseThrow(() -> 
                                new RuntimeException("user not found" + authentication.getName()));
        model.addAttribute("recruiter",users);

        return "recruiter/editprofile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            Authentication authentication,
            @ModelAttribute("recruiter") user formData,
            RedirectAttributes redirectAttributes) {

        String email = authentication.getName();

        System.out.println("Logged-in username: [" + email + "]");

        user recruiterUser = userrepo.findByEmail(email)
                .orElse(null);

        if (recruiterUser == null) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "User not found for login: " + email
            );

            return "redirect:/recruiter/profile/edit";
        }

        recruiterUser.setName(formData.getName());

        userrepo.save(recruiterUser);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Profile updated successfully!"
        );

        return "redirect:/recruiter/profile/edit";
    }
    
}