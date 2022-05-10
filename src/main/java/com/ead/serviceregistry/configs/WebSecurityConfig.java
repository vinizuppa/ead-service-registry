package com.ead.serviceregistry.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Value("${ead.serviceRegistry.username}")
    private String username;

    @Value("${ead.serviceRegistry.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception{//Esse metodo
        http
                .httpBasic()//define Http Basic
                .and()
                .authorizeRequests()//Qualquer uma das requisições, precisarão estar autorizadas
                .anyRequest().authenticated()//Qualquer uma das requisições, precisarão estar autenticadas
                .and()
                .csrf().disable()//Desabilita CSRF
                .formLogin();//Para aparecer o formulario no eureka client, é necessário essa linha
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()//Define tipo de autenticação.
                .withUser(username)//Passamos usuário padrão
                .password(passwordEncoder().encode(password))//passamos senha padrão
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
