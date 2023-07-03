package com.learning.java.crud.springLaMiaPizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

      /* per definire un AuthenticationProvider ho bisogno di:
    - uno UserDetailsService
    - un PasswordEncoder
   */

    // questo è lo UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // questo è il PasswordEncoder (che deduce l'algoritmo di encoding da una stringa nella password
    // stessa)
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/ingredients").hasAuthority("ADMIN")
                .requestMatchers("offers/create").hasAuthority("ADMIN")
                .requestMatchers("papas/create").hasAuthority("ADMIN")
                .requestMatchers("papas/edit/{id}").hasAuthority("ADMIN")
                .requestMatchers("papas/delete/{id}").hasAuthority("ADMIN")
                .requestMatchers("offers/delete/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
                .requestMatchers("papas/{id}").permitAll()
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        return http.build();

    }
}
