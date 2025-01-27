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

import com.generation.crudfarmacia.model.AutenticidadeModel;
import com.generation.crudfarmacia.repository.AutenticidadeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autenticidades")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AutenticidadeController {
	
	@Autowired
	private AutenticidadeRepository autenticidadeRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<AutenticidadeModel>> getAll() {
		return ResponseEntity.ok(autenticidadeRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<AutenticidadeModel> getById(@PathVariable Long id) {

		return autenticidadeRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado"));

	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<AutenticidadeModel>> getByTipo(@PathVariable String tipo) {

		return ResponseEntity.ok(autenticidadeRepository.findAllByTipoContainingIgnoreCase(tipo));
	}

	@PostMapping("/criar")
	public ResponseEntity<AutenticidadeModel> postAutenticidade(@Valid @RequestBody AutenticidadeModel autenticidade) {

		return ResponseEntity.status(HttpStatus.CREATED).body(autenticidadeRepository.save(autenticidade));

	}

	@PutMapping("/atualizar")
	public ResponseEntity<AutenticidadeModel> putAutenticidade(@Valid @RequestBody AutenticidadeModel autenticidade) {

		return autenticidadeRepository.findById(autenticidade.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(autenticidadeRepository.save(autenticidade)))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"A autenticidade a ser atualizada não foi encontrada"));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar/{id}")
	public void delete(@PathVariable Long id) {
		Optional<AutenticidadeModel> autenticidadeOpt = autenticidadeRepository.findById(id);

		if (autenticidadeOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autenticidade não encontrada");
		autenticidadeRepository.deleteById(id);

	}
}
