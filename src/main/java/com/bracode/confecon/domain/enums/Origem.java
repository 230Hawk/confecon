package com.bracode.confecon.domain.enums;




	public enum Origem {

		ANIMAL(1, "Origem Animal"),
		VEGETAL(2, "Origem Vegetal"),
		ARTESANAL(3, "Origem Artesanal"),
		INDUSTRIAL(4, "Origem Industrial"),
		QUIMICA(5, "Origem Quimica");
	
		private int cod;
		private String descricao;
		
		private Origem(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		
		public int getCod() {
			return cod;
		}
		
		public String getDescricao () {
			return descricao;
		}
		
		public static Origem toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (Origem x : Origem.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
		
	}
