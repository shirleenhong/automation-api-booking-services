package com.cwt.bpg.cbt.services.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cwt.bpg.cbt.security.service.TokenService;

@EnableWebSecurity
public class WebSecurityConfig {
	
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
      auth
          .inMemoryAuthentication()
              .withUser("admin").password("secret").roles("ACTUATOR");
  }

  @Configuration
  @Order(1)                                                        
  public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	  
	  @Override
      protected void configure(HttpSecurity http) throws Exception {
          http
              .antMatcher("/manage/**")                               
              .authorizeRequests()
                  .anyRequest().hasRole("ACTUATOR")
                  .and()
              .httpBasic();
      }
      
      @Override
      public void configure(WebSecurity web) throws Exception
      {
          web.ignoring().antMatchers("/app-info", "/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**");
      }
  }    

  @Configuration                                                   
  public static class TokenSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
      @Autowired
      private TokenService tokenService;
      @Override
      protected void configure(HttpSecurity http) throws Exception {
    	  http
    	  .csrf()
    	  .disable().addFilterBefore(new AuthenticationFilter(tokenService), BasicAuthenticationFilter.class);

      }
  }
}

