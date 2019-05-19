package com.api.slcontract.security.config;

import com.api.slcontract.security.jwt.JwtConfigurer;
import com.api.slcontract.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
						.httpBasic().disable()
						.csrf().disable()
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.authorizeRequests()
						.antMatchers("/auth/signup").permitAll()
						.antMatchers("/auth/signin").permitAll()
						.antMatchers("/auth/me").permitAll()
						.antMatchers(HttpMethod.DELETE, "/v1/users/**").hasRole("ADMIN")
						.antMatchers(HttpMethod.GET, "/v1/users/**").hasRole("ADMIN")
						.anyRequest().authenticated()
						.and()
						.apply(new JwtConfigurer(jwtTokenProvider));
		//@formatter:on
	}
}
