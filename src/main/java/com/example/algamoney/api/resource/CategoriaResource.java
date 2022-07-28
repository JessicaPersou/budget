package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.algamoney.api.event.RecursoCriadorEvent;
import com.example.algamoney.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll(); //aqui pega a lista de categorias que já existem e mostra no body
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) { //o validated, está validando valores inválidos
		Categoria categoriaSalva = categoriaRepository.save(categoria); //criando uma nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(categoriaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString()); //aqui escreve a location da uri com o codigo criado

		publisher.publishEvent(new RecursoCriadorEvent(this, response, categoriaSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva); // passa a uri que pode usar para fazer um get depois
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		return this.categoriaRepository.findById(id)
				.map(categoria -> ResponseEntity.ok(categoria)) //caso a categoria exista, mostra com 200 ok
				.orElse(ResponseEntity.notFound().build()); // não encontrado caso seja um id inexistente e 404 not found
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id){
		categoriaRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Categoria atualizar(@PathVariable Long id,@Valid @RequestBody Categoria categoria) {
		Categoria categoriaSalva = categoriaService.atualizar(id,categoria);
		return this.categoriaRepository.save(categoriaSalva);

	}
}
/*Também chamada de Controller é responsável por ligar a model e a view, fazendo com que os models possam ser representados para as views e vice-versa*/