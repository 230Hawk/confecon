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

import com.bracode.confecon.domain.Marca;
import com.bracode.confecon.domain.dto.MarcaDTO;
import com.bracode.confecon.services.MarcaService;

@RestController
@RequestMapping(value="/marcas")
public class MarcaResource {
	
	@Autowired
	private MarcaService marcaService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Marca> find(@PathVariable Integer id) {
		Marca marca = marcaService.find(id);

		return ResponseEntity.ok().body(marca);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MarcaDTO objMarcaDto) {
		Marca objMarca = marcaService.fromDto(objMarcaDto);
		objMarca = marcaService.insert(objMarca);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objMarca.getId()).toUri();	
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody MarcaDTO objMarcaDto, @PathVariable Integer id) {
		Marca objMarca = marcaService.fromDto(objMarcaDto);
		objMarca.setId(id);
		objMarca = marcaService.update(objMarca);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		marcaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MarcaDTO>> findAll() {
		List<Marca> listMarca = marcaService.findAll();
		List<MarcaDTO> listMarcaDTO = listMarca.stream().map(objMarca -> new MarcaDTO(objMarca)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listMarcaDTO);
		}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<MarcaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy,
			@RequestParam(value="direction", defaultValue="ASC")String direction) {
		Page<Marca> listMarca = marcaService.findPage(page, linesPerPage, orderBy, direction);
		Page<MarcaDTO> listMarcaDTO = listMarca.map(objMarca -> new MarcaDTO(objMarca));
		return ResponseEntity.ok().body(listMarcaDTO);
	}
}
