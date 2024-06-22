package com.infy.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.infy.filter.JwtAuthFilter;
import com.infy.service.UserInfoUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	
	  //@Autowired
	//private PasswordEncoder encoder;
	  
	  @Autowired
	    private JwtAuthFilter authFilter;
	
	@Bean
	public UserDetailsService userDetailsService() {
//		//Basic authentication
//		UserDetails admin = User.withUsername("saitej").password(encoder.encode("saitej")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("joe").password(encoder.encode("joe")).roles("USER").build();
//		return new InMemoryUserDetailsManager(admin,user);
		
		// DB based authentication
		return new UserInfoUserDetailsService();
	}
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
//		return http.build();

//		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/products/welcome","/api/products/adduser").permitAll().and()
//				.authorizeHttpRequests().requestMatchers("/api/products/**").authenticated().and().httpBasic().and()
//				.build();
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/products/welcome","/api/products/adduser","/api/products/authenticate").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/api/products/**").authenticated().and() .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
	}
	  
//	  @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        return http.csrf().disable()
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/api/products/welcome", "/api/products/adduser").permitAll()
//	                .requestMatchers("/api/products/**").authenticated()
//	            )
//	            .httpBasic() // Use HTTP Basic authentication
//	            .and()
//	            .build();
//	    }
	
	   @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}

}
