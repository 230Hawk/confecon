package com.bracode.confecon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bracode.confecon.domain.Situacao;

public interface SituacaoRepository extends JpaRepository<Situacao, Integer> {
	
	//public Page<Situacao> findAll(Pageable pageRequest);

}
