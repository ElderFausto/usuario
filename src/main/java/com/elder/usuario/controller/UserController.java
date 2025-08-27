package com.elder.usuario.controller;

import com.elder.usuario.business.UserService;
import com.elder.usuario.business.dto.AddressDTO;
import com.elder.usuario.business.dto.PhoneDTO;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.saveUser(userDTO));
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserDTO usersDTO) {
		Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
										usersDTO.getEmail(),
										usersDTO.getPassword())
		);
		return "Bearer " + jwtUtil.generateToken(authentication.getName());
	}
	
	@GetMapping
	public ResponseEntity<UserDTO> findUsersEmail(@RequestParam("email") String email) {
		return ResponseEntity.ok(userService.searchUserByEmail(email));
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deleteUsersByEmail(@PathVariable String email) {
		userService.deleteUserByEmail(email);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> updateUserData(@RequestBody UserDTO dto,
	                                              @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(userService.updateUserData(token, dto));
	}
	
	@PutMapping("/address")
	public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO dto,
	                                                @RequestParam("id") Long id) {
		return ResponseEntity.ok(userService.updateAddress(id, dto));
	}
	
	@PutMapping("/phone")
	public ResponseEntity<PhoneDTO> updateAddress(@RequestBody PhoneDTO dto,
	                                              @RequestParam("id") Long id) {
		return ResponseEntity.ok(userService.updatePhone(id, dto));
	}
	
	@PostMapping("/address")
	public ResponseEntity<AddressDTO> registerAddress(@RequestBody AddressDTO dto,
	                                                  @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(userService.registerAddress(token, dto));
	}
	
	@PostMapping("/phone")
	public ResponseEntity<PhoneDTO> registerPhone(@RequestBody PhoneDTO dto,
	                                              @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(userService.registerPhone(token, dto));
	}
}
