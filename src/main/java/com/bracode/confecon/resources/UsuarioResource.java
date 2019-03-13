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

import com.bracode.confecon.domain.Usuario;
import com.bracode.confecon.domain.dto.UsuarioDTO;
import com.bracode.confecon.domain.dto.UsuarioNewDTO;
import com.bracode.confecon.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Integer id) {
		Usuario objUsuario = usuarioService.find(id);
		return ResponseEntity.ok().body(objUsuario);
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@RequestParam(value="value") String email) {
		Usuario objUsuario = usuarioService.findByEmail(email);
		return ResponseEntity.ok().body(objUsuario);
	}


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objUsuarioNewDto) {
		Usuario objUsuario = usuarioService.fromDto(objUsuarioNewDto);
		objUsuario = usuarioService.insert(objUsuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objUsuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objUsuarioDto, @PathVariable Integer id) {
		Usuario objUsuario = usuarioService.fromDto(objUsuarioDto);
		objUsuario.setId(id);
		objUsuario = usuarioService.update(objUsuario);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> listUsuario = usuarioService.findAll();
		List<UsuarioDTO> listUsuarioDTO = listUsuario.stream().map(objUsuario -> new UsuarioDTO(objUsuario))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listUsuarioDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Usuario> listUsuario = usuarioService.findPage(page, linesPerPage, orderBy, direction);
		Page<UsuarioDTO> listUsuarioDTO = listUsuario.map(objUsuario -> new UsuarioDTO(objUsuario));
		return ResponseEntity.ok().body(listUsuarioDTO);
	}

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
		URI uri = usuarioService.uploadProfileFile(file);
		return ResponseEntity.created(uri).build();
	}

}
