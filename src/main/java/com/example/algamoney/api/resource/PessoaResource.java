package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa salvaPessoa = pessoaRepository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{idPessoa}")
                .buildAndExpand(salvaPessoa.getIdPessoa()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(salvaPessoa);
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> buscarPorIdPessoa(@PathVariable Long idPessoa){
        Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);
        return pessoa.isPresent() ?ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }
}
