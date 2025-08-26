package com.elder.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "rua")
	private String street;
	
	@Column(name = "numero")
	private Long number;
	
	@Column(name = "complemento")
	private String complement;
	
	@Column(name = "cidade")
	private String city;
	
	@Column(name = "estado")
	private String state;
	
	@Column(name = "cep")
	private String cep;
}
