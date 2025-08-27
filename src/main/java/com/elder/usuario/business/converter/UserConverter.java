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
						.id(addressDTO.getId())
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
		return addressDTO.stream().map(this::forAddressDTO).toList();
	}
	
	public AddressDTO forAddressDTO(Address address) {
		return AddressDTO.builder()
						.id(address.getId())
						.street(address.getStreet())
						.number(address.getNumber())
						.city(address.getCity())
						.complement(address.getComplement())
						.cep(address.getCep())
						.state(address.getState())
						.build();
	}
	
	public List<PhoneDTO> forPhoneListDTO(List<Phone> phoneDTO) {
		return phoneDTO.stream().map(this::forPhoneDTO).toList();
	}
	
	public PhoneDTO forPhoneDTO(Phone phone) {
		return PhoneDTO.builder()
						.id(phone.getId())
						.number(phone.getNumber())
						.ddd(phone.getDdd())
						.build();
	}
	
	public Users updateUser(UserDTO userDTO, Users entity) {
		return Users.builder()
						.name(userDTO.getName() != null ? userDTO.getName() : entity.getName())
						.id(entity.getId())
						.password(userDTO.getPassword() != null ? userDTO.getPassword() : entity.getPassword())
						.email(userDTO.getEmail() != null ? userDTO.getEmail() : entity.getEmail())
						.address(entity.getAddress())
						.phone(entity.getPhone())
						.build();
	}
	
	public Address updateAddress(AddressDTO dto, Address entity) {
		return Address.builder()
						.id(entity.getId())
						.street(dto.getStreet() != null ? dto.getStreet() : entity.getStreet())
						.number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
						.city(dto.getCity() != null ? dto.getCity() : entity.getCity())
						.cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
						.complement(dto.getComplement() != null ? dto.getComplement() : entity.getComplement())
						.state(dto.getState() != null ? dto.getState() : entity.getState())
						.build();
	}
	
	public Phone updatePhone(PhoneDTO dto, Phone entity) {
		return Phone.builder()
						.id(entity.getId())
						.ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
						.number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
						.build();
	}
	
	public Address forAddressEntity(AddressDTO dto, Long idUser) {
		return Address.builder()
						.street(dto.getStreet())
						.number(dto.getNumber())
						.city(dto.getCity())
						.cep(dto.getCep())
						.complement(dto.getComplement())
						.state(dto.getState())
						.user_id(idUser)
						.build();
	}
	
	public Phone forPhoneEntity(PhoneDTO dto, Long idUser) {
		return Phone.builder()
						.number(dto.getNumber())
						.ddd(dto.getDdd())
						.user_id(idUser)
						.build();
	}
}
