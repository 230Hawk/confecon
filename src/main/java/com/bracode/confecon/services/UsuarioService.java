package com.bracode.confecon.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bracode.confecon.domain.Cidade;
import com.bracode.confecon.domain.Endereco;
import com.bracode.confecon.domain.Usuario;
import com.bracode.confecon.domain.dto.UsuarioDTO;
import com.bracode.confecon.domain.dto.UsuarioNewDTO;
import com.bracode.confecon.domain.enums.TipoUser;
import com.bracode.confecon.repositories.UsuarioRepository;
import com.bracode.confecon.security.UserSS;
import com.bracode.confecon.services.exceptions.AuthorizationException;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Usuario find(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(TipoUser.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}

		Optional<Usuario> objusuario = usuarioRepository.findById(id);
		return objusuario.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	@Transactional
	public Usuario insert(Usuario objUsuario) {
		objUsuario.setId(null);
		objUsuario = usuarioRepository.save(objUsuario);
		//enderecoUsuarioRepository.saveAll(objUsuario.getEnderecos_usuario());
		return objUsuario;

	}

	public Usuario update(Usuario objUsuario) {
		Usuario newObjUsuario = find(objUsuario.getId());
		updateData(newObjUsuario, objUsuario);
		return usuarioRepository.save(newObjUsuario);

	}

	public void delete(Integer id) {
		find(id);
		try {
			usuarioRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posssível deletar Usuario com pedidos.");
		}

	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();

	}

	public Usuario findByEmail(String email) {
		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(TipoUser.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado!");
		}
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId() + "Tipo: " + Usuario.class.getName());
		}
		return usuario;
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return usuarioRepository.findAll(pageRequest);

	}

	public Usuario fromDto(UsuarioDTO objUsuarioDto) {
		return new Usuario(objUsuarioDto.getId(), objUsuarioDto.getNome(),
				objUsuarioDto.getEmail(), null, null);
	}

	public Usuario fromDto(UsuarioNewDTO objUsuarioNewDto) {
		Usuario usuario = new Usuario(null, objUsuarioNewDto.getNome(), objUsuarioNewDto.getEmail(), pe.encode(objUsuarioNewDto.getSenha()),
				objUsuarioNewDto.getCpf());

		Cidade cidade = new Cidade(objUsuarioNewDto.getCidadeId(), null, null);

		Endereco endereco_usuario = new Endereco(null, objUsuarioNewDto.getLogradouro(), objUsuarioNewDto.getNumero(),
				objUsuarioNewDto.getComplemento(), objUsuarioNewDto.getBairro(), objUsuarioNewDto.getCep(), null, usuario,
				null, cidade);
		usuario.getEnderecos().add(endereco_usuario);
		usuario.getTelefones().add(objUsuarioNewDto.getTelefone1());
		if (objUsuarioNewDto.getTelefone2() != null) {
			usuario.getTelefones().add(objUsuarioNewDto.getTelefone2());
		}
		if (objUsuarioNewDto.getTelefone3() != null) {
			usuario.getTelefones().add(objUsuarioNewDto.getTelefone3());
		}
		return usuario;
	}

	private void updateData(Usuario newObjUsuario, Usuario objUsuario) {
		newObjUsuario.setNome(objUsuario.getNome());
		newObjUsuario.setEmail(objUsuario.getEmail());
	}

	public URI uploadProfileFile(MultipartFile multipartFile) {

		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado.");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}
}
