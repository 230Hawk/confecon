package com.bracode.confecon.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.bracode.confecon.domain.Marca;

public class MarcaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatorio.")
	@Length(min=2, max=80, message="O tamanho deve ser entre 2 e 80 caracteres.")
	private String nome;

	public MarcaDTO() {
	}
	
	public MarcaDTO(Marca marca) {
		id = marca.getId();
		nome= marca.getNome();
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
