package com.bracode.confecon.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bracode.confecon.domain.Transportadora;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Integer> {
	
	@Transactional(readOnly=true)
	Transportadora findByEmail(String email);

}
