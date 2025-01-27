package com.generation.crudfarmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.crudfarmacia.model.ProdutoModel;
import com.generation.crudfarmacia.repository.CategoriaRepository;
import com.generation.crudfarmacia.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/all")
	public ResponseEntity<List<ProdutoModel>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ProdutoModel> getById(@PathVariable Long id) {

		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

	}

	@GetMapping("/composto/{composto}")
	public ResponseEntity<List<ProdutoModel>> getByComposto(@PathVariable String composto) {

		return ResponseEntity.ok(produtoRepository.findAllByCompostoContainingIgnoreCase(composto));
	}
	
	@PostMapping("/criar")
	public ResponseEntity<ProdutoModel> postProduto(@Valid @RequestBody ProdutoModel produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}


	@PutMapping("/atualizar")
	public ResponseEntity<ProdutoModel> putProduto(@Valid @RequestBody ProdutoModel produto ) {
		
		if (categoriaRepository.existsById(produto.getCategoria().getId()))
			return produtoRepository.findById(produto.getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"O produto não foi encontrado"));
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria inexistente.");
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar/{id}")
	public void deleteProduto(@PathVariable Long id) {
		Optional<ProdutoModel> produtoOpt = produtoRepository.findById(id);

		if (produtoOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
		produtoRepository.deleteById(id);

	}

}
