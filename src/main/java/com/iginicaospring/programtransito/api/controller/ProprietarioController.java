package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.domain.model.Proprietario;
import com.iginicaospring.programtransito.domain.repository.ProprietarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios") // mapeamento de requisição
public class ProprietarioController {

    private ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listar() {
        return proprietarioRepository.findAll();
    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) {
            return proprietarioRepository.findById(proprietarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // método para adiconar novos proprietarios
    @PostMapping // manda, adicionar novos dados
    @ResponseStatus(HttpStatus.CREATED) // enumeração spring que retorna status de "criado"
    public Proprietario adicionar(@RequestBody Proprietario proprietario) {// RequestBody, vincula os parâmetros no corpo da requisição
        return proprietarioRepository.save(proprietario);
    }
}
