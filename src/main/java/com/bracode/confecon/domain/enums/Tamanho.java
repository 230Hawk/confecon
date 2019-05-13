package com.bracode.confecon.domain.enums;



public enum Tamanho {
	

		PP(1, "Tamanho PP"),
		P(2, "Tamanho P"),
		M(3, "Tamanho M"),
		G(4, "Tamanho G"),
		GG(5, "Tamanho GG"),
		EXG(6, "Tamanho EXG"),
		T02(7, "Tamanho 02"),
		T04(8, "Tamanho 04"),
		T06(9, "Tamanho 06"),
		T08(10, "Tamanho 08"),
		T10(11, "Tamanho 10"),
		T12(12, "Tamanho 12"),
		T14(13, "Tamanho 14"),
		T34(14, "Tamanho 34"),
		T36(15, "Tamanho 36"),
		T38(16, "Tamanho 38"),
		T40(17, "Tamanho 40"),
		T42(18, "Tamanho 42"),
		T44(19, "Tamanho 44"),
		TT46(20, "Tamanho 46"),
		T48(22, "Tamanho 48"),
		T50(23, "Tamanho 50"),
		T52(24, "Tamanho 52"),
		T54(25, "Tamanho 54"),
		T56(26, "Tamanho 56");
		
		
		
		private int cod;
		private String descricao;
		
		private Tamanho(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		
		public int getCod() {
			return cod;
		}
		
		public String getDescricao () {
			return descricao;
		}
		
		public static Tamanho toEnum(Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (Tamanho x : Tamanho.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
		
	}
