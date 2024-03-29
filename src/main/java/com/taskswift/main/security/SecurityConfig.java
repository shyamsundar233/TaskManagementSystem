//$Id$
package com.taskswift.main.security;

import javax.sql.DataSource;

import com.taskswift.main.util.CustomLogoutSuccessHandler;
import com.taskswift.main.util.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(req -> req
					.requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/test")).permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.loginPage("/login")
					.successHandler(new LoginSuccessHandler())
					.permitAll()
			)
			.logout((logout) -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessHandler(new CustomLogoutSuccessHandler())
					.logoutSuccessUrl("/login")
			);
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder(DataSource dataSource) {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"select username, password, enabled from users where username=?"
		);
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select username, authority from authorities where username=?"
		);
		
		return jdbcUserDetailsManager;
	}
	
}
