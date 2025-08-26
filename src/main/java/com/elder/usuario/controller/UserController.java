package com.elder.usuario.controller;

import com.elder.usuario.business.UserService;
import com.elder.usuario.business.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(userService.saveUser(userDTO));
	}
}
