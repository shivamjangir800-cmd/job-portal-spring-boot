package com.jobdata.jobdata.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobdata.jobdata.Repositary.applicationrepo;
import com.jobdata.jobdata.Repositary.jobrepo;
import com.jobdata.jobdata.Repositary.userrepo;
import com.jobdata.jobdata.model.Application;
import com.jobdata.jobdata.model.job;
import com.jobdata.jobdata.model.user;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;



@Controller 
@RequestMapping("/candidate")
public class candidates {

    private final jobrepo repos;
    private final userrepo repo;

    private final applicationrepo rp;
    public candidates(jobrepo repos,userrepo repo,applicationrepo rp){
        this.repos = repos;
        this.repo = repo;
        this.rp = rp;
    }

    @GetMapping("/dashboard")
    public String dashboards(Authentication authentication,Model model) {

        user users = repo.findByEmail(authentication.getName())
                    .orElse(null);
        List<job> jobs = repos.findAll();
        model.addAttribute("jobs",jobs);
        model.addAttribute("candidateName",users.getName());
        return "candidate/dashboard";
    }

    @GetMapping("/profile")
    public String getprofile(Authentication authentication,Model model) {
        user users = repo.findByEmail(authentication.getName())
                    .orElseThrow(() -> 
                        new RuntimeException("user not found"));

        model.addAttribute("user",users);
        return "candidate/profile";
    }

    @GetMapping("/jobs")
    public String jobs(@RequestParam(required = false) String keyword, 
                    @RequestParam(required = false) String location,
                    Model model) {

        // remove unnecassry space  
        if(keyword != null){
            keyword.trim();
        }
        if(location != null){
            location.trim();
        }

        if(keyword != null && keyword.isEmpty()){
            keyword = null;
        }

        if(location != null && location.isEmpty()){
            location = null;
        }


        List<job> jobs = repos.searchJobs(keyword, location); 
        model.addAttribute("jobs", jobs); 
        model.addAttribute("keyword", keyword); 
        model.addAttribute("location", location);

        return "candidate/jobs";
    }
    
    @GetMapping("/job_details/{id}")
    public String getjobs(@PathVariable Long id,Model model) {

        job jobs = repos.findById(id).orElse(null);
        model.addAttribute("job",jobs);
        return "candidate/job_details";
    }

    @PostMapping("/jobs/{id}/apply")
    public String getMethodName(@PathVariable Long id
                                ,@RequestParam("resume") MultipartFile resumeurl,
                                @RequestParam("coverLetter") String coverletter,
                                Authentication authentication
                            ,Model model) throws IOException {
        
        String email = authentication.getName();
        
        if(resumeurl.isEmpty()){
            throw new RuntimeException("please select a resume");
        }

        String originalFilename = resumeurl.getOriginalFilename();

        if(originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")){
            throw new RuntimeException("only pdf file save");
        }
        user users = repo.findByEmail(email)
                    .orElseThrow(() ->
                        new RuntimeException("user not found"));

        job jobs = repos.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("job not found"));

        String uploaddir = "upload/resumes/";

        Path uploadpath = Paths.get(uploaddir);

        if(!Files.exists(uploadpath)){
            Files.createDirectories(uploadpath);
        }

        String filename = System.currentTimeMillis() + "_" + originalFilename;

        Path filepPath = uploadpath.resolve(filename);

        Files.copy(
            resumeurl.getInputStream(),
            filepPath,
            StandardCopyOption.REPLACE_EXISTING);
        Application appli = new Application();
        appli.setCandidate(users);
        appli.setJobs(jobs);
        appli.setResumeurl(filename);
        appli.setCoverletter(coverletter);
        appli.setStatus("PENDING");

        rp.save(appli);
        return "redirect:/candidate/applications";
    }

    @GetMapping("/applications")
    public String application(Authentication authentication,Model model) {

        user candidate = repo.findByEmail(authentication.getName())
                        .orElseThrow(() -> 
                            new RuntimeException("candidate not found"));

        List<Application> applic= rp.findByCandidate(candidate);
        
 
        model.addAttribute("applications",applic);
        return "candidate/my_application";
        
    }

    @GetMapping("/applications/{id}")
    public String getapplications(@PathVariable Long id,Model model) {

        Application applic = rp.findById(id)
                            .orElseThrow(() -> 
                                    new RuntimeException("application not found"));

        model.addAttribute("applica",applic);
        return "candidate/applicationdetails";
    }
    

    @GetMapping("/apply/{id}")
    public String getMethodName(@PathVariable Long id,Model model) {

        job jobs = repos.findById(id)
                    .orElseThrow(() -> 
                                new RuntimeException("job not found"));
        model.addAttribute("job",jobs);
        return "candidate/apply";
    }


}
