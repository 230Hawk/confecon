package com.bracode.confecon.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bracode.confecon.domain.MateriaPrima;
import com.bracode.confecon.domain.dto.MateriaPrimaDTO;
import com.bracode.confecon.services.MateriaPrimaService;

@RestController
@RequestMapping(value="/materias")
public class MateriaPrimaResource {
	
		
		@Autowired
		private MateriaPrimaService materiaPrimaService;
		
		@RequestMapping(value="/{id}", method=RequestMethod.GET)
		public ResponseEntity<MateriaPrima> find(@PathVariable Integer id) {
			
		MateriaPrima materiaPrima = materiaPrimaService.find(id);	
			return ResponseEntity.ok().body(materiaPrima);
		}
		
		//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody MateriaPrimaDTO objMateriaPrimaDto) {
			MateriaPrima objMateriaPrima = materiaPrimaService.fromDto(objMateriaPrimaDto);
			objMateriaPrima = materiaPrimaService.insert(objMateriaPrima);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(objMateriaPrima.getId()).toUri();	
			return ResponseEntity.created(uri).build();
		}
		
		//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Void> update(@Valid @RequestBody MateriaPrimaDTO objMateriaPrimaDto, @PathVariable Integer id) {
			MateriaPrima objMateriaPrima = materiaPrimaService.fromDto(objMateriaPrimaDto);
			objMateriaPrima.setId(id);
			objMateriaPrima = materiaPrimaService.update(objMateriaPrima);
			return ResponseEntity.noContent().build();
		}
		
	/*
	 * @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	 * 
	 * @RequestMapping(value="/{id}", method=RequestMethod.DELETE) public
	 * ResponseEntity<Void> delete(@PathVariable Integer id) {
	 * materiaPrimaService.delete(id); return ResponseEntity.noContent().build(); }
	 */
		
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<List<MateriaPrimaDTO>> findAll() {
			List<MateriaPrima> listMateriaPrima = materiaPrimaService.findAll();
			List<MateriaPrimaDTO> listMateriaPrimaDTO = listMateriaPrima.stream().map(objMateriaPrima -> new MateriaPrimaDTO(objMateriaPrima)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listMateriaPrimaDTO);
		}
		
		@RequestMapping(value="/page", method=RequestMethod.GET)
		public ResponseEntity<Page<MateriaPrimaDTO>> findPage(
				@RequestParam(value="page", defaultValue="0") Integer page,
				@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
				@RequestParam(value="orderBy", defaultValue="nome")String orderBy,
				@RequestParam(value="direction", defaultValue="ASC")String direction) {
			Page<MateriaPrima> listMateriaPrima = materiaPrimaService.findPage(page, linesPerPage, orderBy, direction);
			Page<MateriaPrimaDTO> listMateriaPrimaDTO = listMateriaPrima.map(objMateriaPrima -> new MateriaPrimaDTO(objMateriaPrima));
			return ResponseEntity.ok().body(listMateriaPrimaDTO);
		}
	}

