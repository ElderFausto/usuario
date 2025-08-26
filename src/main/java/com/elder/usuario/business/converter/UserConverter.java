package com.elder.usuario.business.converter;

import com.elder.usuario.business.dto.AddressDTO;
import com.elder.usuario.business.dto.PhoneDTO;
import com.elder.usuario.business.dto.UserDTO;
import com.elder.usuario.infrastructure.entity.Address;
import com.elder.usuario.infrastructure.entity.Phone;
import com.elder.usuario.infrastructure.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {
	public Users forUser(UserDTO userDTO) {
		return Users.builder()
						.name(userDTO.getName())
						.email(userDTO.getEmail())
						.password(userDTO.getPassword())
						.address(forAddressList(userDTO.getAddress()))
						.phone(forPhoneList(userDTO.getPhone()))
						.build();
	}
	
	public List<Address> forAddressList(List<AddressDTO> addressDTO) {
		return addressDTO.stream().map(this::forAddress).toList();
	}
	
	public Address forAddress(AddressDTO addressDTO) {
		return Address.builder()
						.street(addressDTO.getStreet())
						.number(addressDTO.getNumber())
						.city(addressDTO.getCity())
						.complement(addressDTO.getComplement())
						.cep(addressDTO.getCep())
						.state(addressDTO.getState())
						.build();
	}
	
	public List<Phone> forPhoneList(List<PhoneDTO> phoneDTO) {
		return phoneDTO.stream().map(this::forPhone).toList();
	}
	
	public Phone forPhone(PhoneDTO phoneDTO) {
		return Phone.builder()
						.number(phoneDTO.getNumber())
						.ddd(phoneDTO.getDdd())
						.build();
	}
	
	// ------------------------------------------------------------
	
	public UserDTO forUserDTO(Users userDTO) {
		return UserDTO.builder()
						.name(userDTO.getName())
						.email(userDTO.getEmail())
						.password(userDTO.getPassword())
						.address(forAddressListDTO(userDTO.getAddress()))
						.phone(forPhoneListDTO(userDTO.getPhone()))
						.build();
	}
	
	public List<AddressDTO> forAddressListDTO(List<Address> addressDTO) {
		return addressDTO.stream().map(this::forAddress).toList();
	}
	
	public AddressDTO forAddress(Address addressDTO) {
		return AddressDTO.builder()
						.street(addressDTO.getStreet())
						.number(addressDTO.getNumber())
						.city(addressDTO.getCity())
						.complement(addressDTO.getComplement())
						.cep(addressDTO.getCep())
						.state(addressDTO.getState())
						.build();
	}
	
	public List<PhoneDTO> forPhoneListDTO(List<Phone> phoneDTO) {
		return phoneDTO.stream().map(this::forPhoneDTO).toList();
	}
	
	public PhoneDTO forPhoneDTO(Phone phoneDTO) {
		return PhoneDTO.builder()
						.number(phoneDTO.getNumber())
						.ddd(phoneDTO.getDdd())
						.build();
	}
	
}
