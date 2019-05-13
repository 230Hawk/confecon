package com.bracode.confecon.domain.enums;




	public enum UM {

		KILO(1, "Kilograma"),
		METRO(2, "Metro"),
		UNIDADE(3, "Unidade");
	
		private int cod;
		private String descricao;
		
		private UM(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		
		public int getCod() {
			return cod;
		}
		
		public String getDescricao () {
			return descricao;
		}
		
		public static UM toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (UM x : UM.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
		
	}
