package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll(); //aqui pega a lista de categorias que já existem e mostra no body
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Validated @RequestBody Categoria categoria, HttpServletResponse response) { //o validated, está validando valores inválidos
		Categoria categoriaSalva = categoriaRepository.save(categoria); //criando uma nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(categoriaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString()); //aqui escreve a location da uri com o codigo criado
		
		return ResponseEntity.created(uri).body(categoriaSalva); // passa a uri que pode usar para fazer um get depois
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		return this.categoriaRepository.findById(id)
				.map(categoria -> ResponseEntity.ok(categoria)) //caso a categoria exista, mostra com 200 ok
				.orElse(ResponseEntity.notFound().build()); // não encontrado caso seja um id inexistente e 404 not found
	}
	
}
/*Também chamada de Controller é responsável por ligar a model e a view, fazendo com que os models possam ser representados para as views e vice-versa*/