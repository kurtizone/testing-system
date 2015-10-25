package com.testing.edu.service.security;

import com.testing.edu.entity.enumeration.UserRole;
import com.testing.edu.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	Logger logger = Logger.getLogger(MyUserDetailsService.class);

	@Autowired
	private UserService userService;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	
		com.testing.edu.entity.User user = userService.findByUsername(username);
		logger.info(user.getUsername());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));
		logger.info(authorities);
		logger.info(authorities.get(0));
		return buildUserForAuthentication(user, authorities);
		
	}


	private User buildUserForAuthentication(com.testing.edu.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.getIsEnabled(), true, true, true, authorities);
	}


}