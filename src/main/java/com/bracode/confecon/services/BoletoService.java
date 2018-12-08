package com.bracode.confecon.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.PagamentoBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoBoleto(PagamentoBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setPagamento(cal.getTime());
	}

}