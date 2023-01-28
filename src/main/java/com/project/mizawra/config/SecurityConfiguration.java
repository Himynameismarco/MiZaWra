package com.project.mizawra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(User.withUsername("user1")
            .password(passwordEncoder().encode("user1Pass"))
            .roles("USER")
            .build());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeHttpRequests()
        .requestMatchers("/login*").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").loginProcessingUrl("/perform_login") // TODO: Create this page - the URL to submit the username and password to
        .defaultSuccessUrl("/home", true)
        .failureUrl("/login_error") //TODO: Create this page
        .and()
        .logout().logoutUrl("/perform_logout"); //TODO: Create this page
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
