package com.npro.UserManagementService.Security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final CustomAuthorisationFilter customAuthorisationFilter;



    public SecurityConfig(UserDetailsService userDetailsService, CustomAuthorisationFilter customAuthorisationFilter, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.customAuthorisationFilter = customAuthorisationFilter;
        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain UIsecurityFilterChain(HttpSecurity httpSecurity,
                                                     CustomAuthenticationFilter loginFilter) throws Exception{
//        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        // set the name of the attribute the CsrfToken will be populated on
//        requestHandler.setCsrfRequestAttributeName("_csrf");
        httpSecurity
//                Disabling CSRF protection for now as application is stateless and using JTW tokens for login. May need to reconfigure in the future.
//                .csrf(csrf -> csrf.csrfTokenRepository(tokenRepository)
//                        .csrfTokenRequestHandler(requestHandler
                .cors((cors) -> cors.configurationSource(urlCorsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/login", "/csrf", "/auth/refreshToken").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(customAuthorisationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(loginFilter);

        return httpSecurity.build();
    }


    UrlBasedCorsConfigurationSource urlCorsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(authenticationProvider());
        return authBuilder.build();
    }

    @Bean
    public CustomAuthenticationFilter loginFilter(AuthenticationManager authMgr, JWTService jwtService, CustomAuthenticationFailureHandler failureHandler) {
        CustomAuthenticationFilter f = new CustomAuthenticationFilter(authMgr, jwtService);
        f.setFilterProcessesUrl("/auth/login");
        f.setAuthenticationFailureHandler(failureHandler);// optional: customise login URL
        return f;
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailure() {
        return new CustomAuthenticationFailureHandler();
    }


}
