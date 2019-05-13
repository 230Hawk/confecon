package com.bracode.confecon.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.bracode.confecon.domain.Transportadora;

//@TransportadoraUpdate
public class TransportadoraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatorio.")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres.")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio.")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres.")
	private String nomeFantasia;
	
	@NotEmpty(message="Preenchimento obrigatorio.")
	@Email(message="Email inválido.")
	private String email;
	

	
	public TransportadoraDTO() {
	}
	
	public TransportadoraDTO(Transportadora transportadora) {
		id = transportadora.getId();
		nome = transportadora.getNome();
		email = transportadora.getEmail();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



}
