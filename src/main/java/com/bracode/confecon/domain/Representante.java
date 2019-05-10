package com.bracode.confecon.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("representante")
public class Representante extends Usuario {
	private static final long serialVersionUID = 1L;

	private Integer comissao1;
	private Integer comissao2;
	
	public Representante() {
		
	}

	public Representante(Integer id, String nome, String email, String senha, String cpf, Integer comissao1,
			Integer comissao2) {
		super(id, nome, email, senha, cpf);
		this.comissao1 = comissao1;
		this.comissao2 = comissao2;
	}



	public Integer getComissao1() {
		return comissao1;
	}

	public void setComissao1(Integer comissao1) {
		this.comissao1 = comissao1;
	}

	public Integer getComissao2() {
		return comissao2;
	}

	public void setComissao2(Integer comissao2) {
		this.comissao2 = comissao2;
	}
	
	
}
