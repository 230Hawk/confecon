package com.bracode.confecon.domain.enums;


public enum TipoMateriaPrima {
	

		JEANS(1, "Jeans"),
		ALGODAO(2, "Algodão"),
		FIBRA(3, "Fibra"),
		LYCRA(4, "Lycra"),
		COURO(5, "Couro"),
		VINIL(6, "Vinil"),
		BOTAO(7, "Botão"),
		ZIPER(8, "Zipper"),
		REBITE(9, "Rebite"),
		ETIQUETA(10, "Etiqueta"),
		DESTACAVEL(11, "Etiqueta esterna"),
		LINHA(12, "Linha");
		
		private int cod;
		private String descricao;
		
		private TipoMateriaPrima(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		
		public int getCod() {
			return cod;
		}
		
		public String getDescricao () {
			return descricao;
		}
		
		public static TipoMateriaPrima toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (TipoMateriaPrima x : TipoMateriaPrima.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
		
	}
