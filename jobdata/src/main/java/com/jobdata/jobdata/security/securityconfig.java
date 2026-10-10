package com.jobdata.jobdata.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class securityconfig {

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                "/auth/login",
                "/register",
                "/auth/register",
                "/auth/forgot-password",
                "/auth/resetpassword",
                "/css/**",
                "/js/**",
                "/images/**"
                ).permitAll()

            .requestMatchers("/candidate/**")
            .hasRole("CANDIDATE")

            .requestMatchers("/recruiter/**")
            .hasRole("RECRUITER")

            .requestMatchers("/resume/**")
            .authenticated()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/auth/login-success",true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    @Bean 
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }
}

