package com.bracode.confecon.resources.utils;

import java.util.ArrayList;
import java.util.List;

public class ALL {
	
	public static List<Integer> decodeIntList(List<?> listaObj) {
		Integer lista = listaObj.size();
		
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<lista; i++) {
			list.add(i+1);
		}
		return list;
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

}
