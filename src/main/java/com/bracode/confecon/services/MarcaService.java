package com.bracode.confecon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.Marca;
import com.bracode.confecon.domain.dto.MarcaDTO;
import com.bracode.confecon.repositories.MarcaRepository;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class MarcaService {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	public Marca find(Integer id) {
		Optional<Marca> objmarca = marcaRepository.findById(id);
		return objmarca.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Marca.class.getName()));
	}

	public Marca insert(Marca objMarca){
		objMarca.setId(null);
		return marcaRepository.save(objMarca);
		
	}
	
	public Marca update(Marca objMarca){
		Marca newObjMarca = find(objMarca.getId());
		updateData(newObjMarca, objMarca);
		return marcaRepository.save(newObjMarca);
		
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			marcaRepository.deleteById(id);
			} 
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é posssível deletar Marca com produtos cadastrados nela.");
			}
	}
	
	public List<Marca> findAll() {
		return	marcaRepository.findAll();
		
	}
	
	public Page<Marca> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return marcaRepository.findAll(pageRequest);

	}
	
	public Marca fromDto(MarcaDTO objMarcaDto) {
		return new Marca(objMarcaDto.getId(), objMarcaDto.getNome());
	}
	
	private void updateData(Marca newObjMarca, Marca objMarca) {
		newObjMarca.setNome(objMarca.getNome());
		
	}
}
