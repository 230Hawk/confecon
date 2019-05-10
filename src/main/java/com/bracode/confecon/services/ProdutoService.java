package com.bracode.confecon.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bracode.confecon.domain.Grupo;
import com.bracode.confecon.domain.Marca;
import com.bracode.confecon.domain.Produto;
import com.bracode.confecon.domain.Situacao;
import com.bracode.confecon.repositories.GrupoRepository;
import com.bracode.confecon.repositories.MarcaRepository;
import com.bracode.confecon.repositories.ProdutoRepository;
import com.bracode.confecon.repositories.SituacaoRepository;
import com.bracode.confecon.services.exceptions.DataIntegrityException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Autowired
	private SituacaoRepository situacaoRepository;

	public Produto find(Integer id) {
		Optional<Produto> objproduto = produtoRepository.findById(id);
		return objproduto.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> searchNomeGrupo(String nome,
			List<Integer> idsGrupos,
			Integer page,
			Integer linesPerPage,
			String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Grupo> grupos = grupoRepository.findAllById(idsGrupos);
		return produtoRepository.findDistinctByNomeContainingAndGruposIn(nome, grupos,  pageRequest);	
	}
	
	public Page<Produto> search(String nome,
			List<Integer> idsGrupos,
			List<Integer> idsMarcas,
			List<Integer> idsSituacoes,
			Integer page,
			Integer linesPerPage,
			String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Grupo> grupos = grupoRepository.findAllById(idsGrupos);
		List<Marca> marcas = marcaRepository.findAllById(idsMarcas);
		List<Situacao> situacoes = situacaoRepository.findAllById(idsSituacoes);
		return produtoRepository.findDistinctByNomeContainingAndGruposInAndMarcasInAndSituacoesIn(nome, grupos, marcas, situacoes, pageRequest);	
	}

	

	
	@Transactional
	public Produto insert(Produto objProduto){
		objProduto.setId(null);
		objProduto.setGrupos(objProduto.getGrupos());
		objProduto = produtoRepository.save(objProduto);
		System.out.println(objProduto.getDescricao());
		System.out.println(objProduto.getGrupos());
		grupoRepository.saveAll(objProduto.getGrupos());
		return objProduto;
		
	}
	
	public Produto update(Produto objProduto){
		Produto newObjProduto = find(objProduto.getId());
		updateData(newObjProduto, objProduto);
		return produtoRepository.save(newObjProduto);
		
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			produtoRepository.deleteById(id);
			} 
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é posssível deletar Produto com pedidos.");
			}

	}
	
	public List<Produto> findAll() {
		return	produtoRepository.findAll();
		
	}
	
	public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return produtoRepository.findAll(pageRequest);

	}

	
	private void updateData(Produto newObjCliente, Produto objCliente) {
		newObjCliente.setNome(objCliente.getNome());
	
	}
	
	
}
