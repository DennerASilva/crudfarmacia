package com.generation.crudfarmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.crudfarmacia.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel,Long> {
	
	public List<ProdutoModel> findAllByCompostoContainingIgnoreCase(@Param("composto") String composto);
}
