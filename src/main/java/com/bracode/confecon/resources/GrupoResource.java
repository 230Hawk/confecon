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

import com.bracode.confecon.domain.Grupo;
import com.bracode.confecon.domain.dto.GrupoDTO;
import com.bracode.confecon.services.GrupoService;

@RestController
@RequestMapping(value="/grupos")
public class GrupoResource {
	
	@Autowired
	private GrupoService grupoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Grupo> find(@PathVariable Integer id) {
		
	Grupo grupo = grupoService.find(id);	
		return ResponseEntity.ok().body(grupo);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody GrupoDTO objGrupoDto) {
		Grupo objGrupo = grupoService.fromDto(objGrupoDto);
		objGrupo = grupoService.insert(objGrupo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objGrupo.getId()).toUri();	
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody GrupoDTO objGrupoDto, @PathVariable Integer id) {
		Grupo objGrupo = grupoService.fromDto(objGrupoDto);
		objGrupo.setId(id);
		objGrupo = grupoService.update(objGrupo);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		grupoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<GrupoDTO>> findAll() {
		List<Grupo> listGrupo = grupoService.findAll();
		List<GrupoDTO> listGrupoDTO = listGrupo.stream().map(objGrupo -> new GrupoDTO(objGrupo)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listGrupoDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<GrupoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy,
			@RequestParam(value="direction", defaultValue="ASC")String direction) {
		Page<Grupo> listGrupo = grupoService.findPage(page, linesPerPage, orderBy, direction);
		Page<GrupoDTO> listGrupoDTO = listGrupo.map(objGrupo -> new GrupoDTO(objGrupo));
		return ResponseEntity.ok().body(listGrupoDTO);
	}
}
