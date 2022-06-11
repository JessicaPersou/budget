package budget.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import budget.model.Categoria;
import budget.repository.CategoriaRepository;

@RestController // retorno vai converter para json
@RequestMapping("/categorias") // mapeando a requisição
public class CategoriaResource { // é o reecurso de categoria, vai expor, tudo que esteja relacionada a ela, é o controlador, recebe requisiçoes

	@Autowired // injeção de dependencia // procura na categoria repository e entrega na categoria resource
	private CategoriaRepository categoriaRepository;  // esta implementando as operaçoes do repository
	
	
	@GetMapping // mapeando o get para categorias
	public List<Categoria> listar() {  	
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) // para mostrar 201 created que serve essa anotação
	//requestbody transforma o que foi enviado no jason em objeto com essa anotação e retorna como categoria salva
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva); // não precisa da anotaçao pois aqui já mostra e retorna location e a criação
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaPorCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById(codigo)
				.map(categoria->ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
}
