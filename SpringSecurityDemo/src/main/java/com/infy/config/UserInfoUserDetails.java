package com.infy.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.infy.entity.UserInfo;
import com.infy.service.UserInfoUserDetailsService;

public class UserInfoUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails() {
		
	}
	
	public UserInfoUserDetails(UserInfo userInfo) {
		this.name = userInfo.getName();
		this.password= userInfo.getPassword();
		this.authorities = Arrays
				.stream(userInfo.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

}
