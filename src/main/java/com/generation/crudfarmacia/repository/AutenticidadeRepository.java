package com.generation.crudfarmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.crudfarmacia.model.AutenticidadeModel;

public interface AutenticidadeRepository extends JpaRepository<AutenticidadeModel,Long>{

	public List<AutenticidadeModel> findAllByTipoContainingIgnoreCase(@Param("tipo") String tipo);
}
