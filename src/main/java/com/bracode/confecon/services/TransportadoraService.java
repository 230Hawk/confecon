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
import com.bracode.confecon.domain.Endereco;
import com.bracode.confecon.domain.Transportadora;
import com.bracode.confecon.domain.dto.TransportadoraDTO;
import com.bracode.confecon.domain.dto.TransportadoraNewDTO;
import com.bracode.confecon.domain.enums.TipoJuridico;
import com.bracode.confecon.repositories.EnderecoRepository;
import com.bracode.confecon.repositories.TransportadoraRepository;
import com.bracode.confecon.security.UserSS;
import com.bracode.confecon.services.exceptions.AuthorizationException;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class TransportadoraService {

	@Autowired
	private TransportadoraRepository transportadoraRepository;

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

	public Transportadora find(Integer id) {

		

		/*
		 * UserSS user = UserService.authenticated();
		 * if (user == null || !user.hasRole(TipoUser.ADMIN) &&
		 * !id.equals(user.getId())) { throw new
		 * AuthorizationException("Acesso negado!"); }
		 */

		Optional<Transportadora> objtransportadora = transportadoraRepository.findById(id);
		return objtransportadora.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Transportadora.class.getName()));
	}

	@Transactional
	public Transportadora insert(Transportadora objTransportadora) {
		objTransportadora.setId(null);
		objTransportadora = transportadoraRepository.save(objTransportadora);
		enderecoRepository.saveAll(objTransportadora.getEnderecos());
		return objTransportadora;

	}

	public Transportadora update(Transportadora objTransportadora) {
		Transportadora newObjTransportadora = find(objTransportadora.getId());
		updateData(newObjTransportadora, objTransportadora);
		return transportadoraRepository.save(newObjTransportadora);

	}

	public void delete(Integer id) {
		find(id);
		try {
			transportadoraRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posssível deletar Transportadora com pedidos.");
		}

	}

	public List<Transportadora> findAll() {
		return transportadoraRepository.findAll();

	}

	
	public Page<Transportadora> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return transportadoraRepository.findAll(pageRequest);

	}

	public Transportadora fromDto(TransportadoraDTO objTransportadoraDto) {
		return new Transportadora(objTransportadoraDto.getId(), objTransportadoraDto.getNome(), objTransportadoraDto.getEmail(), null, null, null, null,
				null);
	}

	public Transportadora fromDto(TransportadoraNewDTO objTransportadoraNewDto) {
		Transportadora transportadora = new Transportadora(null, objTransportadoraNewDto.getNome(), objTransportadoraNewDto.getNomeFantasia(), objTransportadoraNewDto.getEmail(),
				objTransportadoraNewDto.getCpfCnpj(), objTransportadoraNewDto.getiEstadual(), TipoJuridico.toEnum(objTransportadoraNewDto.getTipo()),
				 objTransportadoraNewDto.getContato());

		Cidade cidade = new Cidade(objTransportadoraNewDto.getCidadeId(), null, null);

		Endereco endereco = new Endereco(null, objTransportadoraNewDto.getLogradouro(), objTransportadoraNewDto.getNumero(),
				objTransportadoraNewDto.getComplemento(), objTransportadoraNewDto.getBairro(), objTransportadoraNewDto.getCep(), null, null, transportadora,
				 null, cidade);
		transportadora.getEnderecos().add(endereco);
		transportadora.getTelefones().add(objTransportadoraNewDto.getTelefone1());
		if (objTransportadoraNewDto.getTelefone2() != null) {
			transportadora.getTelefones().add(objTransportadoraNewDto.getTelefone2());
		}
		if (objTransportadoraNewDto.getTelefone3() != null) {
			transportadora.getTelefones().add(objTransportadoraNewDto.getTelefone3());
		}
		return transportadora;
	}

	private void updateData(Transportadora newObjTransportadora, Transportadora objTransportadora) {
		newObjTransportadora.setNome(objTransportadora.getNome());
		newObjTransportadora.setEmail(objTransportadora.getEmail());
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
