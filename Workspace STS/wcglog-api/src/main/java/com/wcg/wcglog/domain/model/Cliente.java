package com.wcg.wcglog.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity // Definindo que a classe Cliente representa uma Entidade
@Table(name = "cliente") // Nome da tabela no banco de dados
public class Cliente {

	@EqualsAndHashCode.Include // EqualsAndHashCode apenas para id
	@Id // Chave Primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Banco de dados controla a estrategia
	private Long id;

	//Validacoes exigem o @Valid no POST e PUT
	@NotBlank   
	@Size(max = 60)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	@NotBlank
	@Size(max = 20)
	@Column(name = "fone") // Especifica o nome da coluna no banco de dados
	private String telefone;

	
	
	
}
