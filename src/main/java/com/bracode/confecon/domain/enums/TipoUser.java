package com.bracode.confecon.domain.enums;

public enum TipoUser {

	ADMIN(1, "Administrador"),
	FREE(2, "Gratuito"),
	BASIC(3, "Basico"),
	MEDIUM(4,"Medio"),
	ADVANCED(5, "Avançado"),
	PREMIUM(6, "Prêmio"),
	TOP(7, "Topo");
	
	private int cod;
	private String descricao;
	
	private TipoUser(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static TipoUser toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (TipoUser x : TipoUser.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}