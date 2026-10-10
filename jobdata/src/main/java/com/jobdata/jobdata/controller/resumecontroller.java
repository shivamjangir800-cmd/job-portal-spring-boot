package com.jobdata.jobdata.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("/resume")
public class resumecontroller {

    private final String uploaddir = "upload/resumes";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> resource(@PathVariable String filename) throws MalformedURLException {

        Path filepath = Paths.get(uploaddir).resolve(filename).normalize();

        Resource resource = new UrlResource(filepath.toUri());

        if (!resource.exists()) {
             return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok() 
            .contentType(MediaType.APPLICATION_PDF) 
            .header( HttpHeaders.CONTENT_DISPOSITION
                , "inline; filename=\"" + resource.getFilename() + 
                "\"" ) .body(resource);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Object> downloadresume(@PathVariable String filename) throws IOException {

        Path filepath = Paths.get(uploaddir).resolve(filename).normalize();

        Resource resource = new UrlResource(filepath.toUri());

        if(!resource.exists()){
            return ResponseEntity.notFound().build();
        }
        
        
        return ResponseEntity.ok() 
                .contentType(MediaType.APPLICATION_PDF) 
                .header( HttpHeaders.CONTENT_DISPOSITION, 
                    "attachment; filename=\"" + resource.getFilename() + "\"" ) 
                    .body(resource);
    }
    
    
}

