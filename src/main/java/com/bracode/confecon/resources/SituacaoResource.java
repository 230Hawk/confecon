package com.bracode.confecon.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bracode.confecon.domain.Situacao;
import com.bracode.confecon.domain.dto.SituacaoDTO;
import com.bracode.confecon.services.SituacaoService;

@RestController
@RequestMapping(value="/situacoes")
public class SituacaoResource {
	
	@Autowired
	private SituacaoService situacaoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Situacao> find(@PathVariable Integer id) {
		Situacao situacao = situacaoService.find(id);
		return ResponseEntity.ok().body(situacao);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody SituacaoDTO objSituacaoDto) {
		Situacao objSituacao = situacaoService.fromDto(objSituacaoDto);
		objSituacao = situacaoService.insert(objSituacao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objSituacao.getId()).toUri();	
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody SituacaoDTO objSituacaoDto, @PathVariable Integer id) {
		Situacao objSituacao = situacaoService.fromDto(objSituacaoDto);
		objSituacao.setId(id);
		objSituacao = situacaoService.update(objSituacao);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		situacaoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<SituacaoDTO>> findAll() {
		List<Situacao> listSituacao = situacaoService.findAll();
		List<SituacaoDTO> listSituacaoDTO = listSituacao.stream().map(objSituacao -> new SituacaoDTO(objSituacao)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listSituacaoDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<SituacaoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy,
			@RequestParam(value="direction", defaultValue="ASC")String direction) {
		Page<Situacao> listSituacao = situacaoService.findPage(page, linesPerPage, orderBy, direction);
		Page<SituacaoDTO> listSituacaoDTO = listSituacao.map(objSituacao -> new SituacaoDTO(objSituacao));
		return ResponseEntity.ok().body(listSituacaoDTO);
	}
}
