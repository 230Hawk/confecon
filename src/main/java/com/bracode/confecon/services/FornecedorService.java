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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bracode.confecon.domain.Cidade;
import com.bracode.confecon.domain.Fornecedor;
import com.bracode.confecon.domain.dto.FornecedorDTO;
import com.bracode.confecon.domain.dto.FornecedorNewDTO;
import com.bracode.confecon.domain.Endereco;
import com.bracode.confecon.repositories.FornecedorRepository;
import com.bracode.confecon.repositories.EnderecoRepository;
import com.bracode.confecon.security.UserSS;
import com.bracode.confecon.services.exceptions.AuthorizationException;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Fornecedor find(Integer id) {

		

		/*
		 * UserSS user = UserService.authenticated();
		 * if (user == null || !user.hasRole(TipoUser.ADMIN) &&
		 * !id.equals(user.getId())) { throw new
		 * AuthorizationException("Acesso negado!"); }
		 */

		Optional<Fornecedor> objfornecedor = fornecedorRepository.findById(id);
		return objfornecedor.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Fornecedor.class.getName()));
	}

	@Transactional
	public Fornecedor insert(Fornecedor objFornecedor) {
		objFornecedor.setId(null);
		objFornecedor = fornecedorRepository.save(objFornecedor);
		enderecoRepository.saveAll(objFornecedor.getEnderecos());
		return objFornecedor;

	}

	public Fornecedor update(Fornecedor objFornecedor) {
		Fornecedor newObjFornecedor = find(objFornecedor.getId());
		updateData(newObjFornecedor, objFornecedor);
		return fornecedorRepository.save(newObjFornecedor);

	}

	public void delete(Integer id) {
		find(id);
		try {
			fornecedorRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posssível deletar Fornecedor com pedidos.");
		}

	}

	public List<Fornecedor> findAll() {
		return fornecedorRepository.findAll();

	}

	
	public Page<Fornecedor> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return fornecedorRepository.findAll(pageRequest);

	}

	public Fornecedor fromDto(FornecedorDTO objFornecedorDto) {
		return new Fornecedor(objFornecedorDto.getId(), objFornecedorDto.getNome(), objFornecedorDto.getEmail(), null, null, null, null,
				null);
	}

	public Fornecedor fromDto(FornecedorNewDTO objFornecedorNewDto) {
		Fornecedor fornecedor = new Fornecedor(null, objFornecedorNewDto.getNome(), objFornecedorNewDto.getNomeFantasia(), objFornecedorNewDto.getEmail(),
				objFornecedorNewDto.getCpfCnpj(), objFornecedorNewDto.getiEstadual(), 
				 null, null);

		Cidade cidade = new Cidade(objFornecedorNewDto.getCidadeId(), null, null);

		Endereco endereco = new Endereco(null, objFornecedorNewDto.getLogradouro(), objFornecedorNewDto.getNumero(),
				objFornecedorNewDto.getComplemento(), objFornecedorNewDto.getBairro(), objFornecedorNewDto.getCep(), null, null, fornecedor, cidade
				);
		fornecedor.getEnderecos().add(endereco);
		fornecedor.getTelefones().add(objFornecedorNewDto.getTelefone1());
		if (objFornecedorNewDto.getTelefone2() != null) {
			fornecedor.getTelefones().add(objFornecedorNewDto.getTelefone2());
		}
		if (objFornecedorNewDto.getTelefone3() != null) {
			fornecedor.getTelefones().add(objFornecedorNewDto.getTelefone3());
		}
		return fornecedor;
	}

	private void updateData(Fornecedor newObjFornecedor, Fornecedor objFornecedor) {
		newObjFornecedor.setNome(objFornecedor.getNome());
		newObjFornecedor.setEmail(objFornecedor.getEmail());
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
