package com.wigell.wigellpadel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/api/v5/customers").hasRole("ADMIN")
                        .requestMatchers("/api/v5/register").hasRole("ADMIN")
                        .requestMatchers("/api/v5/deletebooking/**").hasRole("ADMIN")
                        .requestMatchers("/api/v5/updateinfo").hasRole("ADMIN")
                        .requestMatchers("/api/v5/bookings/**").hasRole("ADMIN")
                        .requestMatchers("/api/v5/availability").hasRole("USER")
                        .requestMatchers("/api/v5/bookings").hasRole("USER")
                        .requestMatchers("/api/v5/mybookings/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("USER", "ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
