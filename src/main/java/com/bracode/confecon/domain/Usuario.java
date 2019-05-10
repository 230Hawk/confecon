package com.bracode.confecon.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import com.bracode.confecon.domain.enums.TipoUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") 
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	
	@JsonIgnore
	private String senha;
	private String cpf;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "TIPOUSER")
	private Set<Integer> tipouser = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "TELEFONE_USUARIO")
	private Set<String> telefones_usuario = new HashSet<>();
	
	public Usuario() {
		
	}

	
	public Usuario(Integer id, String nome, String email, String senha,
			 String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		addTipoUser(TipoUser.AUXILIAR);

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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	public Set<TipoUser> getTipouser() {
		return tipouser.stream().map(x -> TipoUser.toEnum(x)).collect(Collectors.toSet());
	}

	public void setTipouser(Set<Integer> tipouser) {
		this.tipouser = tipouser;
	}
	
	public void addTipoUser(TipoUser tipoUser) {
		tipoUser.add(tipoUser.getCod());
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public List<Endereco> getEnderecos() {
		return enderecos;
	}


	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}


	public Set<String> getTelefones() {
		return telefones_usuario;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones_usuario = telefones;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
