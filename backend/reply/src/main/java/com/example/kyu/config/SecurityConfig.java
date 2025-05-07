package com.example.kyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

        private final UserDetailsService userDetailsService;

        public SecurityConfig(UserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/login", "/signup", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated())
                        .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/hello", true)
                                .failureUrl("/login?error=true"))
                        .logout(logout -> logout
                                .logoutSuccessUrl("/login?logout=true")
                                .permitAll())
                        .csrf(csrf -> csrf.disable()); // ✅ 변경된 부분

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                        .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder
                        .userDetailsService(userDetailsService)
                        .passwordEncoder(passwordEncoder());
                return authenticationManagerBuilder.build();
        }
}
