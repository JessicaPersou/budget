package budget.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
