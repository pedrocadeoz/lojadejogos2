package com.generation.lojadejogos.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.generation.lojadejogos.model.Produtos;
import com.generation.lojadejogos.repository.CategoriaRepository;
import com.generation.lojadejogos.repository.ProdutosRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Produtos> getById(@PathVariable Long id) {
		return produtosRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Produtos>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(produtosRepository.findAllByTituloContainingIgnoreCase(titulo));

	}

	@PostMapping
	public ResponseEntity<Produtos> postProdutos(@Valid @RequestBody Produtos produtos) {
		if (categoriaRepository.existsById(produtos.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos));

		return ResponseEntity.notFound().build();

	}

	@PutMapping
	public ResponseEntity<Produtos> putProdutos(@Valid @RequestBody Produtos produtos) {
		if (produtosRepository.existsById(produtos.getId())) {
			return categoriaRepository.findById(produtos.getCategoria().getId())
					.map(resposta -> { 
						return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produtos));
		})
					.orElse(ResponseEntity.notFound().build());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProdutos(@PathVariable Long id) {

		return produtosRepository.findById(id).map(resposta -> {
			produtosRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		})

				.orElse(ResponseEntity.notFound().build());

	}

}
