package com.bracode.confecon.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.MateriaPrima;
import com.bracode.confecon.domain.dto.MateriaPrimaDTO;
import com.bracode.confecon.repositories.MateriaPrimaRepository;

@Service
public class MateriaPrimaService {

	@Autowired
	private MateriaPrimaRepository materiaPrimaRepository;

	public MateriaPrima find(Integer id) {
		Optional<MateriaPrima> objmateriaPrima = materiaPrimaRepository.findById(id);
		return objmateriaPrima.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + MateriaPrima.class.getName(), null));
	}

	public MateriaPrima insert(MateriaPrima objMateriaPrima) {
		objMateriaPrima.setId(null);
		return materiaPrimaRepository.save(objMateriaPrima);

	}

	
	  public MateriaPrima update(MateriaPrima objMateriaPrima){ MateriaPrima
	  newObjMateriaPrima = find(objMateriaPrima.getId());
	  updateData(newObjMateriaPrima, objMateriaPrima); return
	  materiaPrimaRepository.save(newObjMateriaPrima);
	  
	  }
	  
	/*
	 * public void delete(Integer id) { find(id); try {
	 * materiaPrimaRepository.deleteById(id); } catch
	 * (DataIntegrityViolationException e) { throw new
	 * DataIntegrityException("Não é posssível deletar MateriaPrima com produtos cadastrados nele."
	 * ); }
	 * 
	 * }
	 */
	 

	public List<MateriaPrima> findAll() {
		return materiaPrimaRepository.findAll();

	}

	public MateriaPrima fromDto(MateriaPrimaDTO objMateriaPrimaDto) {
		return new MateriaPrima(objMateriaPrimaDto.getId(), null, objMateriaPrimaDto.getNome(), null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	public Page<MateriaPrima> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return materiaPrimaRepository.findAll(pageRequest);

	}
	
	private void updateData(MateriaPrima newObjMateriaPrima, MateriaPrima objMateriaPrima) {
		newObjMateriaPrima.setNome(objMateriaPrima.getNome());
		
	}
}
