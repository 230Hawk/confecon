package com.bracode.confecon.domain.dto;

import java.util.Date;

import com.bracode.confecon.domain.Fornecedor;
import com.bracode.confecon.domain.MateriaPrima;

public class MateriaPrimaDTO {

	private Integer id;
	private Integer codigo;
	private String descricao;
	private String nome;
	private Integer tipo;
	private String referencia;
	private Fornecedor fornecedor;
	private Integer um;
	private Integer tamanho;
	private Double custo;
	private Number desperdicio;
	private Number aproveitamento;
	private String composicao;
	private String lote;
	private Date fabricacao;
	private Integer origem;

	public MateriaPrimaDTO() {
		
	}

	public MateriaPrimaDTO(Integer id, Integer codigo, String descricao, String nome, Integer tipo, String referencia,
			Fornecedor fornecedor, Integer um, Integer tamanho, Double custo, Number desperdicio, Number aproveitamento,
			String composicao, String lote, Date fabricacao, Integer origem) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.nome = nome;
		this.tipo = tipo;
		this.referencia = referencia;
		this.fornecedor = fornecedor;
		this.um = um;
		this.tamanho = tamanho;
		this.custo = custo;
		this.desperdicio = desperdicio;
		this.aproveitamento = aproveitamento;
		this.composicao = composicao;
		this.lote = lote;
		this.fabricacao = fabricacao;
		this.origem = origem;
	}

	public MateriaPrimaDTO(MateriaPrima objMateriaPrima) {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getUm() {
		return um;
	}

	public void setUm(Integer um) {
		this.um = um;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public Number getDesperdicio() {
		return desperdicio;
	}

	public void setDesperdicio(Number desperdicio) {
		this.desperdicio = desperdicio;
	}

	public Number getAproveitamento() {
		return aproveitamento;
	}

	public void setAproveitamento(Number aproveitamento) {
		this.aproveitamento = aproveitamento;
	}

	public String getComposicao() {
		return composicao;
	}

	public void setComposicao(String composicao) {
		this.composicao = composicao;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Date getFabricacao() {
		return fabricacao;
	}

	public void setFabricacao(Date fabricacao) {
		this.fabricacao = fabricacao;
	}

	public Integer getOrigem() {
		return origem;
	}

	public void setOrigem(Integer origem) {
		this.origem = origem;
	}

}

