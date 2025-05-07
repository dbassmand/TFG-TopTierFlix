package com.tfg.TopTierFlix.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService usuarioServicio;

    @Autowired // Inyecta el bean de BCryptPasswordEncoder creado en PasswordEncoderConfig
    private BCryptPasswordEncoder passwordEncoder;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioServicio);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize													//Spring Securirty añade ROLE_ al método hasRole()
                        .requestMatchers("/registro**", "/js/**", "/css/**", "/img/**", "/login").permitAll() 	// Permitir acceso sin autenticar a registro, recursos estáticos, página principal y login
                        .requestMatchers("/admin/**").hasRole("ADMIN") 											// Solo los usuarios con el rol ADMIN pueden acceder a /admin/**
                        .requestMatchers("/user/**").hasRole("USER")   											// Solo los usuarios con el rol USER pueden acceder a /user/**
                        .requestMatchers("/peliculas/**").authenticated() 										// Los usuarios autenticados pueden acceder a /peliculas/**
                        .anyRequest().authenticated() 															// Cualquier otra petición requiere autenticación
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}