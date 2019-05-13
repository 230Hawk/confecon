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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bracode.confecon.domain.Transportadora;
import com.bracode.confecon.domain.dto.TransportadoraDTO;
import com.bracode.confecon.domain.dto.TransportadoraNewDTO;
import com.bracode.confecon.services.TransportadoraService;

@RestController
@RequestMapping(value = "/transportadoras")
public class TransportadoraResource {

	@Autowired
	private TransportadoraService transportadoraService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Transportadora> find(@PathVariable Integer id) {
		Transportadora objTransportadora = transportadoraService.find(id);
		return ResponseEntity.ok().body(objTransportadora);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TransportadoraNewDTO objTransportadoraNewDto) {
		Transportadora objTransportadora = transportadoraService.fromDto(objTransportadoraNewDto);
		objTransportadora = transportadoraService.insert(objTransportadora);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objTransportadora.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody TransportadoraDTO objTransportadoraDto, @PathVariable Integer id) {
		Transportadora objTransportadora = transportadoraService.fromDto(objTransportadoraDto);
		objTransportadora.setId(id);
		objTransportadora = transportadoraService.update(objTransportadora);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		transportadoraService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TransportadoraDTO>> findAll() {
		List<Transportadora> listTransportadora = transportadoraService.findAll();
		List<TransportadoraDTO> listTransportadoraDTO = listTransportadora.stream().map(objTransportadora -> new TransportadoraDTO(objTransportadora))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listTransportadoraDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<TransportadoraDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Transportadora> listTransportadora = transportadoraService.findPage(page, linesPerPage, orderBy, direction);
		Page<TransportadoraDTO> listTransportadoraDTO = listTransportadora.map(objTransportadora -> new TransportadoraDTO(objTransportadora));
		return ResponseEntity.ok().body(listTransportadoraDTO);
	}

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = transportadoraService.uploadProfileFile(file);
		return ResponseEntity.created(uri).build();
	}

}
