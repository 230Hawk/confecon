package com.bracode.confecon.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bracode.confecon.domain.Grupo;
import com.bracode.confecon.domain.Marca;
import com.bracode.confecon.domain.Produto;
import com.bracode.confecon.domain.Situacao;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndGruposIn(String nome, List<Grupo> grupos, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndMarcasIn(String nome, List<Marca> marcas, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.grupos gru, obj.marcas mar, obj.situacoes sit WHERE obj.nome LIKE %:nome% AND gru IN :grupos AND mar IN :marcas AND sit IN :situacoes")
	Page<Produto> findDistinctByNomeContainingAndGruposInAndMarcasInAndSituacoesIn(@Param("nome") String nome, @Param("grupos") List<Grupo> grupos, @Param("marcas") List<Marca> marcas, @Param("situacoes") List<Situacao> situacoes, Pageable pageRequest);
	
	
	
	@Transactional(readOnly=true)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.situacoes sit WHERE obj.nome LIKE %:nome% AND sit IN :situacoes")
	Page<Produto> findDistinctByNomeContainingAndSituacoesIn(@Param("nome") String nome, @Param("situacoes") 
	List<Situacao> categorias, Pageable pageRequest);
	//Page<Produto> findDistinctByNomeContainingAndSituacoesIn(String nome, List<Situacao> categorias, Pageable pageRequest);
	
	

}


	
