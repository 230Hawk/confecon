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

import com.bracode.confecon.domain.Fornecedor;
import com.bracode.confecon.domain.dto.FornecedorDTO;
import com.bracode.confecon.domain.dto.FornecedorNewDTO;
import com.bracode.confecon.services.FornecedorService;

@RestController
@RequestMapping(value = "/fornecedors")
public class FornecedorResource {

	@Autowired
	private FornecedorService fornecedorService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Fornecedor> find(@PathVariable Integer id) {
		Fornecedor objFornecedor = fornecedorService.find(id);
		return ResponseEntity.ok().body(objFornecedor);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody FornecedorNewDTO objFornecedorNewDto) {
		Fornecedor objFornecedor = fornecedorService.fromDto(objFornecedorNewDto);
		objFornecedor = fornecedorService.insert(objFornecedor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objFornecedor.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody FornecedorDTO objFornecedorDto, @PathVariable Integer id) {
		Fornecedor objFornecedor = fornecedorService.fromDto(objFornecedorDto);
		objFornecedor.setId(id);
		objFornecedor = fornecedorService.update(objFornecedor);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		fornecedorService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FornecedorDTO>> findAll() {
		List<Fornecedor> listFornecedor = fornecedorService.findAll();
		List<FornecedorDTO> listFornecedorDTO = listFornecedor.stream().map(objFornecedor -> new FornecedorDTO(objFornecedor))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listFornecedorDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<FornecedorDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Fornecedor> listFornecedor = fornecedorService.findPage(page, linesPerPage, orderBy, direction);
		Page<FornecedorDTO> listFornecedorDTO = listFornecedor.map(objFornecedor -> new FornecedorDTO(objFornecedor));
		return ResponseEntity.ok().body(listFornecedorDTO);
	}

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = fornecedorService.uploadProfileFile(file);
		return ResponseEntity.created(uri).build();
	}

}
