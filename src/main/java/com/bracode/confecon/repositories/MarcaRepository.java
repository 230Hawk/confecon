package com.bracode.confecon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bracode.confecon.domain.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
	
	//public Page<Marca> findAll(Pageable pageRequest);

}
