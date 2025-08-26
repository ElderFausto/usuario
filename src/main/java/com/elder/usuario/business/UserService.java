package com.elder.usuario.business;

import com.elder.usuario.business.converter.UserConverter;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.entity.Users;
import com.elder.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	
	public UserDTO saveUser(UserDTO userDTO) {
		Users user = userConverter.forUser(userDTO);
		return userConverter.forUserDTO(userRepository.save(user));
	}
}
