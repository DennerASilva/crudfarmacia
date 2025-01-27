package com.generation.crudfarmacia.model;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class ProdutoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 20, message = "O campo Nome deverá ter entre 3 e 20 caracteres")
	private String nome;

	@NotNull
	@Range(min=0,message="O campo quantidade não pode ser negativo")
	private int quantidade;
	
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo Composto Químico deve ter no mínimo 5 e no máximo 50 caracteres")
	private String composto;
	
	@NotBlank
	@Size(min = 8, max = 8, message = "O campo Nome deve ter 8 caracteres e ser dos seguintes valores: original ou genérico.")
	private String autenticidade;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private CategoriaModel categoria;

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComposto() {
		return composto;
	}

	public void setComposto(String composto) {
		this.composto = composto;
	}

	public String getAutenticidade() {
		return autenticidade;
	}

	public void setAutenticidade(String autenticidade) {
		this.autenticidade = autenticidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
