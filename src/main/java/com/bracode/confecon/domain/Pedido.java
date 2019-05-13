package com.bracode.confecon.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pedido  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;
	
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="representante_id")
	private Representante representante;
	

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "transportadora_id")
	private Transportadora transportadora;


	@ManyToOne
	@JoinColumn(name="endereco_entrega_id")
	private Endereco enderecoEntrega;
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
		
	}


	public Pedido(Integer id, Date instante, Cliente cliente, Representante representante, Endereco enderecoEntrega, Transportadora transportadora) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.representante = representante;
		this.enderecoEntrega = enderecoEntrega;
		this.transportadora = transportadora;
		
	}

	public double getValorTotal() {
		double soma = 0.0;
		for(ItemPedido ip : itens ) {
			soma = soma + ip.getSubTotal();
			
		}
		
		return soma;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}
	
	public Pagamento getPagamento() {
		return pagamento;
	}



	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	

	public Representante getRepresentante() {
		return representante;
	}


	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}


	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}



	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	
	
	
	public Transportadora getTransportadora() {
		return transportadora;
	}


	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}


	public Set<ItemPedido> getItens() {
		return itens;
	}


	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido Numero. ");
		builder.append(getId());
		builder.append(", Instante. ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente. ");
		builder.append(getCliente().getNome());
		builder.append(", Representante. ");
		builder.append(getRepresentante().getNome());
		builder.append(", Situação. ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\nDetalhes:\n");	
		for(ItemPedido itemPedido : getItens()) {
			builder.append(itemPedido.toString());
		}
		builder.append("Valor Total: ");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}





	
	
}
