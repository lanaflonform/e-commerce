package com.projet.ecommerce.entrypoint.controller;

import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http
//				.csrf().disable()
//				.antMatcher("/utilisateur/**")
//				.authorizeRequests()
//				.antMatchers(HttpMethod.OPTIONS, "**").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.addFilterBefore(new AuthenticationFilter(utilisateurBusiness), BasicAuthenticationFilter.class)
//				.headers();
	}

}
