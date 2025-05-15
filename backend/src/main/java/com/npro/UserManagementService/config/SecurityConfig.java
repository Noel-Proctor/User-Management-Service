package com.npro.UserManagementService.config;

import com.npro.UserManagementService.controllers.CsrfController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CsrfController csrfController) throws Exception{
//        httpSecurity.csrf().disable();
//        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();

        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // set the name of the attribute the CsrfToken will be populated on
        requestHandler.setCsrfRequestAttributeName("_csrf");

        httpSecurity
                .csrf(csrf -> csrf.csrfTokenRepository(tokenRepository)
                        .csrfTokenRequestHandler(requestHandler))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER_MANAGER")
                                .anyRequest().authenticated()).
                formLogin((form)->
                        form
                                .loginProcessingUrl("/authentication/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/api/v1/user/loginTest", true)
                                .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/authentication/login").permitAll());

        return httpSecurity.build();
    }





}
