package com.sm.admDecretos.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticateService)
                .passwordEncoder( new BCryptPasswordEncoder());
     //   SHA2
     //       https://emn178.github.io/online-tools/sha256.html
    }

  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
//                .antMatchers("/**","/v1/**", "/v2/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }*/

    @Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .cors() // Agregar la configuración de CORS
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/**").authenticated()
        .and()
        .httpBasic()
        .and()
        .csrf()
        .disable();
}






}