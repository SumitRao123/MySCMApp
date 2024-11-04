package com.SCM.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.SCM.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    AuthFailureHandler authFailureHandler;

     @Autowired
     SecurityCustomUserDetailService securityCustomUserDetailService;

     @Autowired
     OAuthSuccessHandler oaAuthSuccessHandler;

    @Bean
    public AuthenticationProvider gAuthenticationProvider(){
           
        DaoAuthenticationProvider  daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
     }

     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         

       // used to configure the url
        http.authorizeHttpRequests((authorize)->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
       //  agar hame kuch bhi change karna hua form se related toh ham yaha pe ayenge : form login
        http.formLogin(formLogin ->{
           formLogin.loginPage("/login");
           formLogin.loginProcessingUrl("/authenticate");
           formLogin.successForwardUrl("/user/dashboard");
           
           formLogin.usernameParameter("email");
           formLogin.passwordParameter("password");

           formLogin.failureHandler(authFailureHandler);
        });
          
        http.csrf(AbstractHttpConfigurer :: disable);

        http.logout(formLogout ->{
            formLogout.logoutUrl("/do-logout");
            formLogout.logoutSuccessUrl("/login?logout=true");
        });

        http.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oaAuthSuccessHandler);
        });
  
        return http.build();
     }

     @Bean 
        PasswordEncoder  passwordEncoder(){
         return new BCryptPasswordEncoder();
      }
}
