package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.AuthService;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/persons/**").permitAll()
                                .requestMatchers("/api/adm/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/funcionario/**").permitAll() 
                                .requestMatchers("/api/compra/**").permitAll()
                                .requestMatchers("/api/estoque/**").hasAnyAuthority("FUNCIONARIO","ADMIN")
                                .requestMatchers("/api/produtos/**").permitAll()
                                .requestMatchers("api/venda/**").hasAnyAuthority("FUNCIONARIO","ADMIN")
                                .requestMatchers("api/cliente/**").permitAll()
                                .requestMatchers("/api/carrinho/**").hasAnyAuthority("CLIENTE", "ADMIN") 
        
                )
                .addFilterBefore(new JwtFilter(authService), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/auth/login", "/api/persons/create");
    }
}
