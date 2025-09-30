package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.domain.model.Proprietario;
import com.iginicaospring.programtransito.domain.repository.ProprietarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor // criar para todas as variáveis de instância
@RestController
public class ProprietarioController {

    @Autowired // usa para injetar em uma variável de instância
    private ProprietarioRepository proprietarioRepository;

    @GetMapping("/proprietarios")
    public List<Proprietario> listar() {
        return proprietarioRepository.findAll(); // busca todo os proprietários(em lista)
    }

    // método para realizar buscar por id
    @GetMapping("/proprietarios/{proprietarioId}") // variável de caminho
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) { // anotação para vincular a variável de caminho
        Optional<Proprietario> proprietario = proprietarioRepository.findById(proprietarioId);
        if (proprietario.isPresent()) {
            return ResponseEntity.ok(proprietario.get());
        }
        return ResponseEntity.notFound().build(); // retornar um erro 404
       // return optional.orElse(null); -> obter o conteúdo dentro do Optional
    }
}
