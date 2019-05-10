package com.bracode.confecon.domain.dto;

import java.io.Serializable;

import com.bracode.confecon.domain.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Cidade cidade) {
		id = cidade.getId();
		nome = cidade.getNome();
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
}
