package com.elder.usuario.controller;

import com.elder.usuario.business.UserService;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.entity.Users;
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
	public ResponseEntity<Users> findUsersEmail(@RequestParam("email") String email) {
		return ResponseEntity.ok(userService.searchUserByEmail(email));
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deleteUsersByEmail(@PathVariable String email) {
		userService.deleteUserByEmail(email);
		return ResponseEntity.ok().build();
	}
}
