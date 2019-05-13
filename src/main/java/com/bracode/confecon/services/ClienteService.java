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
import com.bracode.confecon.domain.Cliente;
import com.bracode.confecon.domain.Endereco;
import com.bracode.confecon.domain.dto.ClienteDTO;
import com.bracode.confecon.domain.dto.ClienteNewDTO;
import com.bracode.confecon.domain.enums.TipoJuridico;

import com.bracode.confecon.repositories.ClienteRepository;
import com.bracode.confecon.repositories.EnderecoRepository;
import com.bracode.confecon.security.UserSS;
import com.bracode.confecon.services.exceptions.AuthorizationException;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

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

	public Cliente find(Integer id) {

		

		/*
		 * UserSS user = UserService.authenticated();
		 * if (user == null || !user.hasRole(TipoUser.ADMIN) &&
		 * !id.equals(user.getId())) { throw new
		 * AuthorizationException("Acesso negado!"); }
		 */

		Optional<Cliente> objcliente = clienteRepository.findById(id);
		return objcliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente objCliente) {
		objCliente.setId(null);
		objCliente = clienteRepository.save(objCliente);
		enderecoRepository.saveAll(objCliente.getEnderecos());
		return objCliente;

	}

	public Cliente update(Cliente objCliente) {
		Cliente newObjCliente = find(objCliente.getId());
		updateData(newObjCliente, objCliente);
		return clienteRepository.save(newObjCliente);

	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posssível deletar Cliente com pedidos.");
		}

	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();

	}

	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);

	}

	public Cliente fromDto(ClienteDTO objClienteDto) {
		return new Cliente(objClienteDto.getId(), objClienteDto.getNome(), objClienteDto.getEmail(), null, null, null, null,
				null);
	}

	public Cliente fromDto(ClienteNewDTO objClienteNewDto) {
		Cliente cliente = new Cliente(null, objClienteNewDto.getNome(), objClienteNewDto.getNomeFantasia(), objClienteNewDto.getEmail(),
				objClienteNewDto.getCpfCnpj(), objClienteNewDto.getiEstadual(), TipoJuridico.toEnum(objClienteNewDto.getTipo()),
				 objClienteNewDto.getContato());

		Cidade cidade = new Cidade(objClienteNewDto.getCidadeId(), null, null);

		Endereco endereco = new Endereco(null, objClienteNewDto.getLogradouro(), objClienteNewDto.getNumero(),
				objClienteNewDto.getComplemento(), objClienteNewDto.getBairro(), objClienteNewDto.getCep(), null, cliente,
				null, null, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objClienteNewDto.getTelefone1());
		if (objClienteNewDto.getTelefone2() != null) {
			cliente.getTelefones().add(objClienteNewDto.getTelefone2());
		}
		if (objClienteNewDto.getTelefone3() != null) {
			cliente.getTelefones().add(objClienteNewDto.getTelefone3());
		}
		return cliente;
	}

	private void updateData(Cliente newObjCliente, Cliente objCliente) {
		newObjCliente.setNome(objCliente.getNome());
		newObjCliente.setEmail(objCliente.getEmail());
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
