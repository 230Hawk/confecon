package com.bracode.confecon.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.Cidade;
import com.bracode.confecon.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Integer estado_id){
		return cidadeRepository.findCidades(estado_id);
	}
}
