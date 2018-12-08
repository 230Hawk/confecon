package com.bracode.confecon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.Situacao;
import com.bracode.confecon.domain.dto.SituacaoDTO;
import com.bracode.confecon.repositories.SituacaoRepository;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class SituacaoService {
	
	@Autowired
	private SituacaoRepository situacaoRepository;
	
	public Situacao	 find(Integer id) {
		Optional<Situacao> objsituacao = situacaoRepository.findById(id);
		return objsituacao.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Situacao.class.getName()));
	}
	
	public Situacao insert(Situacao objSituacao){
		objSituacao.setId(null);
		return situacaoRepository.save(objSituacao);
		
	}
	
	public Situacao update(Situacao objSituacao){
		Situacao newObjSituacao = find(objSituacao.getId());
		updateData(newObjSituacao, objSituacao);
		return situacaoRepository.save(newObjSituacao);
		
	}

	public void delete(Integer id) {
		find(id);
		try {
			situacaoRepository.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é posssível deletar Situação com produtos cadastrados nela.");
			}
	}
	
	public List<Situacao> findAll() {
		return	situacaoRepository.findAll();
		
	}
	
	
	
	public Page<Situacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return situacaoRepository.findAll(pageRequest);

	}
	
	public Situacao fromDto(SituacaoDTO objSituacaoDto) {
		return new Situacao(objSituacaoDto.getId(), objSituacaoDto.getNome());
	}
	
	private void updateData(Situacao newObjSituacao, Situacao objSituacao) {
		newObjSituacao.setNome(objSituacao.getNome());
		
	}
}
