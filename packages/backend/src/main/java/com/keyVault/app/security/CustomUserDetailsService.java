package com.keyVault.app.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keyVault.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		com.keyVault.app.entity.User user = userRepository.findById(Integer.parseInt(id))
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email : " + id));
	
		return new UserDetailsImpl(user);
	}
}
