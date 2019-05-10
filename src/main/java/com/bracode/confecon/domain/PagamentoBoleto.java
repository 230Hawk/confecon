package com.bracode.confecon.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.bracode.confecon.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;


@Entity
@JsonTypeName("pagamentoBoleto")
public class PagamentoBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date vencimento;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date pagamento;
	
	public PagamentoBoleto() {
		
	}

	public PagamentoBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date vencimento, Date pagamento) {
		super(id, estado, pedido);
		
		this.vencimento = vencimento;
		this.pagamento = pagamento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Date getPagamento() {
		return pagamento;
	}

	public void setPagamento(Date pagamento) {
		this.pagamento = pagamento;
	}
	
	
	

}
