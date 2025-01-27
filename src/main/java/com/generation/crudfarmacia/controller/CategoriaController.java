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

import com.generation.crudfarmacia.model.CategoriaModel;
import com.generation.crudfarmacia.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/all")
	public ResponseEntity<List<CategoriaModel>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {

		return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado"));

	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<CategoriaModel>> getByNome(@PathVariable String nome) {

		return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping("/criar")
	public ResponseEntity<CategoriaModel> postCategoria(@Valid @RequestBody CategoriaModel categoria) {

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

	}

	@PutMapping("/atualizar")
	public ResponseEntity<CategoriaModel> putCategoria(@Valid @RequestBody CategoriaModel categoria) {

		return categoriaRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria)))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"A categoria a ser atualizada não foi encontrada"));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar/{id}")
	public void delete(@PathVariable Long id) {
		Optional<CategoriaModel> categoriaOpt = categoriaRepository.findById(id);

		if (categoriaOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		categoriaRepository.deleteById(id);

	}
}
