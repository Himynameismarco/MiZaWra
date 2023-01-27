package com.project.mizawra.models.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user1 = User.withUsername("user1")
        .password(passwordEncoder().encode("user1Pass"))
        .roles("USER")
        .build();
    UserDetails user2 = User.withUsername("user2")
        .password(passwordEncoder().encode("user2Pass"))
        .roles("USER")
        .build();
    UserDetails admin = User.withUsername("admin")
        .password(passwordEncoder().encode("adminPass"))
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user1, user2, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/admin/**")
        .hasRole("ADMIN")
        .antMatchers("/anonymous*")
        .anonymous()
        .antMatchers("/login*")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("src/main/resources/templates/login.html")
        .loginProcessingUrl("/perform_login") // TODO: Create this page - the URL to submit the username and password to
        .defaultSuccessUrl("src/main/resources/templates/home.html", true)
        .failureUrl("/login.html?error=true") //TODO: Create this page
        .failureHandler(authenticationFailureHandler())
        .and()
        .logout()
        .logoutUrl("/perform_logout") //TODO: Create this page
        .deleteCookies("JSESSIONID")
        .logoutSuccessHandler(logoutSuccessHandler());
    return http.build();
  }

  private LogoutSuccessHandler logoutSuccessHandler() {
    //TODO implement body
    return null;
  }

  private AuthenticationFailureHandler authenticationFailureHandler() {
    //TODO implement body
    return null;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
