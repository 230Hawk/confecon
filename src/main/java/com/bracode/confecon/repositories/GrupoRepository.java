package com.bracode.confecon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bracode.confecon.domain.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {


	
	//public Page<Grupo> findAll(Pageable pageRequest);
	
	
}
