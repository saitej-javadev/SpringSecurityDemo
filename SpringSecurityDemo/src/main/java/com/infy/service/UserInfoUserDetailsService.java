package com.infy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.infy.config.UserInfoUserDetails;
import com.infy.entity.UserInfo;
import com.infy.repos.UserInfoRepository;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public UserInfoUserDetailsService() {
		
	}
    

	  @Override
	    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
	        Optional<UserInfo> userInfo = userInfoRepository.findByName(name);
	        return userInfo.map(UserInfoUserDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + name));
	        }

}
