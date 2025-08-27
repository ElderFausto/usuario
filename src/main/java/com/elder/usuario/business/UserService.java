package com.elder.usuario.business;

import com.elder.usuario.business.converter.UserConverter;
import com.elder.usuario.business.dto.AddressDTO;
import com.elder.usuario.business.dto.PhoneDTO;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.entity.Address;
import com.elder.usuario.infrastructure.entity.Phone;
import com.elder.usuario.infrastructure.entity.Users;
import com.elder.usuario.infrastructure.exceptions.ConflictException;
import com.elder.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.elder.usuario.infrastructure.repository.AddressRepository;
import com.elder.usuario.infrastructure.repository.PhoneRepository;
import com.elder.usuario.infrastructure.repository.UserRepository;
import com.elder.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AddressRepository addressRepository;
	private final PhoneRepository phoneRepository;
	
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
	
	public UserDTO searchUserByEmail(String email) {
		try {
			return userConverter.forUserDTO(
							userRepository.findByEmail(email)
											.orElseThrow(
															() -> new ResourceNotFoundException("Email não encontrado" + email)
											)
			);
			
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Email nao encontrado" + email);
		}
		
	}
	
	public void deleteUserByEmail(String email) {
		userRepository.deleteByEmail(email);
	}
	
	public UserDTO updateUserData(String token, UserDTO dto) {
		// aqui busca o email do usuario pelo token (tira a obrigatoriedade do email)
		String email = jwtUtil.extractUsername(token.substring(7));
		// criptografia de senha
		dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);
		
		// busca os dados do usuario no banco de dados
		Users usersEntity = userRepository.findByEmail(email).orElseThrow(() ->
						new ResourceNotFoundException("Email não localizado"));
		// mesclou os dados que recebemos na requisicao DTO com os dados do banco de dados
		Users user = userConverter.updateUser(dto, usersEntity);
		// salva os dados do usuario convertido e depois pegou o retorno e converte para userDTO
		return userConverter.forUserDTO(userRepository.save(user));
	}
	
	public AddressDTO updateAddress(Long idAddress, AddressDTO addressDTO) {
		Address entity = addressRepository.findById(idAddress).orElseThrow(() ->
						new ResourceNotFoundException("Id não encontrado" + idAddress));
		
		Address address = userConverter.updateAddress(addressDTO, entity);
		
		return userConverter.forAddressDTO(addressRepository.save(address));
	}
	
	public PhoneDTO updatePhone(Long idPhone, PhoneDTO dto) {
		Phone entity = phoneRepository.findById(idPhone).orElseThrow(() ->
						new ResourceNotFoundException("Id não encontrado" + idPhone));
		
		Phone phone = userConverter.updatePhone(dto, entity);
		
		return userConverter.forPhoneDTO(phoneRepository.save(phone));
	}
}
