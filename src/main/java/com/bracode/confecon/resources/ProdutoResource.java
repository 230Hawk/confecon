package com.bracode.confecon.resources;

import java.net.URI;
import java.util.List;

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

import com.bracode.confecon.domain.Produto;
import com.bracode.confecon.domain.dto.ProdutoDTO;
import com.bracode.confecon.resources.utils.URL;
import com.bracode.confecon.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
/*  @Autowired
	private GrupoService grupoService;
	
	@Autowired
	private MarcaService marcaService;
	
	@Autowired
	private SituacaoService situacaoService;
	*/
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
	Produto produto = produtoService.find(id);	
		return ResponseEntity.ok().body(produto);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Produto objProduto) {
		objProduto = produtoService.insert(objProduto);
		System.out.println(objProduto.getGrupos());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objProduto.getId()).toUri();	
		return ResponseEntity.created(uri).build();
	}

	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPageNomeGrupo(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="grupos", defaultValue="") String grupos,
			@RequestParam(value="marcas", defaultValue="") String marcas,
			@RequestParam(value="situacoes", defaultValue="") String situacoes,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
	
		List<Integer> idsGrupos = URL.decodeIntList(grupos);
		/*if(idsGrupos.equals(3000)) {
			idsGrupos = ALL.decodeIntList(grupoService.findAll());
		}*/
		List<Integer> idsMarcas = URL.decodeIntList(marcas);
	/*	if(idsMarcas.equals(3000)) {
			idsMarcas = ALL.decodeIntList(marcaService.findAll());
		}*/
		List<Integer> idsSituacoes = URL.decodeIntList(situacoes);
	/*	if(idsSituacoes.equals(3000)) {
			idsGrupos = ALL.decodeIntList(situacaoService.findAll());
		}*/
		Page<Produto> list = produtoService.search(nomeDecoded, idsGrupos, idsMarcas, idsSituacoes, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));  
		
		System.out.println("IdsGrupo" + idsGrupos);
		System.out.println("IdsMarcas" + idsMarcas);
		System.out.println("IdSituacao" + idsSituacoes);
		
		return ResponseEntity.ok().body(listDto);

	}
	
	

}
