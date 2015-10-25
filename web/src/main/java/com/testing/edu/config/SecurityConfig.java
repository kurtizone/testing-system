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
				.antMatchers("/resources/assets/**", "/resources/app/welcome/**",
						"/application/**", "/calibrationTests/**" /*Some one has to move these tests out to verificator page!*/
						, "/calibrationTestData/**").permitAll()

				.antMatchers("/resources/app/admin/**", "/admin/**").hasAnyAuthority("ADMIN")

				.antMatchers("/uploadFile/**").fullyAuthenticated()

				.antMatchers("/resources/app/**").hasAnyAuthority("PROVIDER_EMPLOYEE", "PROVIDER_ADMIN", "CALIBRATOR_EMPLOYEE", "CALIBRATOR_ADMIN", "STATE_VERIFICATOR_EMPLOYEE", "STATE_VERIFICATOR_ADMIN")
				.antMatchers("/employee/admin/**").hasAnyAuthority( "PROVIDER_ADMIN", "CALIBRATOR_ADMIN", "STATE_VERIFICATOR_ADMIN")

				.antMatchers("/provider", "/provider/employee/**").hasAnyAuthority("PROVIDER_EMPLOYEE", "PROVIDER_ADMIN")
				.antMatchers("/provider/admin/**").hasAuthority("STUDENT")

				.antMatchers("/calibrator", "/calibrator/employee/**").hasAnyAuthority("CALIBRATOR_EMPLOYEE", "CALIBRATOR_ADMIN")
				.antMatchers("/calibrator/admin/**").hasAuthority("CALIBRATOR_ADMIN")

				.antMatchers("/verificator", "/verificator/employee/**").hasAnyAuthority("STATE_VERIFICATOR_EMPLOYEE", "STATE_VERIFICATOR_ADMIN")
				.antMatchers("/verificator/admin/**").hasAuthority("LECTURER")

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
