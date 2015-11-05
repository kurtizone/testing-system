package com.testing.edu.config;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;
import com.testing.edu.config.AjaxAuthenticationSuccessHandler;
import com.testing.edu.service.security.SecurityUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = Logger.getLogger(SecurityConfig.class);

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		logger.info("confugure");
		logger.info(auth.userDetailsService(securityUserDetailsService).passwordEncoder(new BCryptPasswordEncoder()));
		auth.userDetailsService(securityUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
		http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);

		http
				.csrf()
				.ignoringAntMatchers("/uploadFile/**")
				.and()
				.authorizeRequests()
				.antMatchers("/resources/app/admin/**", "/admin/**").hasAnyAuthority("ADMIN")
				.antMatchers("/resources/app/student/**", "/student/**").hasAuthority("STUDENT")
				.antMatchers("/resources/app/lecturer/**", "/lecturer/**").hasAuthority("LECTURER")
				.and()
				.formLogin()
				.defaultSuccessUrl("/")
				.loginProcessingUrl("/authenticate")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
				.loginPage("/")
				.permitAll()
				.and()
				.httpBasic()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.logoutUrl("/logout")
				.permitAll();

	}
}
