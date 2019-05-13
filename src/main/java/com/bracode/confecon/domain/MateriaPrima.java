package com.bracode.confecon.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bracode.confecon.domain.enums.TipoJuridico;
import com.bracode.confecon.domain.enums.TipoMateriaPrima;

@Entity
public class MateriaPrima implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public MateriaPrima() {
		
	}

	public MateriaPrima(Integer id, Integer codigo, String descricao, String nome, TipoMateriaPrima tipo, String referencia,
			Fornecedor fornecedor, Integer um, Integer tamanho, Double custo, Number desperdicio, Number aproveitamento,
			String composicao, String lote, Date fabricacao, Integer origem) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.nome = nome;
		this.tipo = (tipo==null) ? null : tipo.getCod();
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

	public TipoMateriaPrima getTipo() {
		return TipoMateriaPrima.toEnum(tipo);
	}

	public void setTipo(TipoMateriaPrima tipo) {
		this.tipo = tipo.getCod();
	}


	public void setTipo(TipoJuridico tipo) {
		this.tipo = tipo.getCod();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MateriaPrima other = (MateriaPrima) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
}
