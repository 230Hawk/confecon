package com.bracode.confecon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.Grupo;
import com.bracode.confecon.domain.dto.GrupoDTO;
import com.bracode.confecon.repositories.GrupoRepository;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;

	public Grupo find(Integer id) {
		Optional<Grupo> objgrupo = grupoRepository.findById(id);
		return objgrupo.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Grupo.class.getName()));
	}
	
	public Grupo insert(Grupo objGrupo){
		objGrupo.setId(null);
		return grupoRepository.save(objGrupo);
		
	}
	
	
	
	public Grupo update(Grupo objGrupo){
		Grupo newObjGrupo = find(objGrupo.getId());
		updateData(newObjGrupo, objGrupo);
		return grupoRepository.save(newObjGrupo);
		
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		grupoRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posssível deletar Grupo com produtos cadastrados nele.");
		}

	}
	
	public List<Grupo> findAll() {
		return	grupoRepository.findAll();
		
	}
	
	public Page<Grupo> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return grupoRepository.findAll(pageRequest);

	}
	
	public Grupo fromDto(GrupoDTO objGrupoDto) {
		return new Grupo(objGrupoDto.getId(), objGrupoDto.getNome());
	}
	
	private void updateData(Grupo newObjGrupo, Grupo objGrupo) {
		newObjGrupo.setNome(objGrupo.getNome());
		
	}
}
