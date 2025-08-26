package com.elder.usuario.business;

import com.elder.usuario.business.converter.UserConverter;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.entity.Users;
import com.elder.usuario.infrastructure.exceptions.ConflictException;
import com.elder.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.elder.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final PasswordEncoder passwordEncoder;
	
	public UserDTO saveUser(UserDTO userDTO) {
		emailExists(userDTO.getEmail());
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		Users user = userConverter.forUser(userDTO);
		return userConverter.forUserDTO(userRepository.save(user));
	}
	
	public void emailExists(String email) {
		try {
			boolean exists = checkEmailExists(email);
			if (exists) {
				throw new ConflictException("Email already exists: " + email);
			}
		} catch (ConflictException e) {
			throw new ConflictException("Error checking email existence: " + e.getMessage());
		}
	}
	
	public boolean checkEmailExists(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Users searchUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
						() -> new ResourceNotFoundException("Email não encontrado" + email));
	}
	public void deleteUserByEmail(String email) {
		userRepository.deleteByEmail(email);
	}
}
