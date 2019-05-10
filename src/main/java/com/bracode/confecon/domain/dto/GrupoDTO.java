package com.bracode.confecon.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.bracode.confecon.domain.Grupo;

public class GrupoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatorio.")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres.")
	private String nome;
	
	public GrupoDTO() {
	}
	
	public GrupoDTO(Grupo grupo) {
	id = grupo.getId();
	nome = grupo.getNome();
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
	
	
}
