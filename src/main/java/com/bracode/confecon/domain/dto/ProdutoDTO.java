package com.bracode.confecon.domain.dto;

import java.io.Serializable;

import com.bracode.confecon.domain.Produto;

public class ProdutoDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String idOriginal;
	private String nome;
	private String descricao;
	private String aplicacao;
	private Double frete;
	private Double preco;

	public ProdutoDTO(Produto objProduto) {
		id = objProduto.getId();
		idOriginal = objProduto.getIdOriginal();
		nome = objProduto.getNome();
		descricao = objProduto.getDescricao();
		aplicacao = objProduto.getAplicacao();
		frete = objProduto.getFrete();
		preco = objProduto.getPreco();
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdOriginal() {
		return idOriginal;
	}

	public void setIdOriginal(String idOriginal) {
		this.idOriginal = idOriginal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(String aplicacao) {
		this.aplicacao = aplicacao;
	}

	public Double getFrete() {
		return frete;
	}

	public void setFrete(Double frete) {
		this.frete = frete;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
