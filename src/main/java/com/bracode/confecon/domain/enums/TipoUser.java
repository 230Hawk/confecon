package com.bracode.confecon.domain.enums;

public enum TipoUser {

	ADMIN(1, "HOLE_ADMIN"),
	PRODUCAO(2, "ROLE_PRODUCAO"),
	REPRESENTANTE(3, "ROLE_REPRESENTANTE"),
	AUXILIAR(4,"ROLE_AUXILIAR");
	
	
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

	public void add(int cod2) {
		// TODO Auto-generated method stub
		
	}
	
}