package com.bracode.confecon.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.bracode.confecon.domain.Grupo;
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
	
	private List<Grupo> grupos;
	//private List<Integer> marcas;
	//private List<Integer> situacoes;
	
	
	
	

	public ProdutoDTO(Produto objProduto) {
		id = objProduto.getId();
		idOriginal = objProduto.getIdOriginal();
		nome = objProduto.getNome();
		descricao = objProduto.getDescricao();
		aplicacao = objProduto.getAplicacao();
		frete = objProduto.getFrete();
		preco = objProduto.getPreco();
		grupos = objProduto.getGrupos();
	
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



	public List<Grupo> getGrupos() {
		return grupos;
	}



	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	

	
}
