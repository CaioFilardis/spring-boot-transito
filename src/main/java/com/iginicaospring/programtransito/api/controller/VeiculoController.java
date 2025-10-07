package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.domain.exception.NegocioException;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import com.iginicaospring.programtransito.domain.repository.VeiculoRepository;
import com.iginicaospring.programtransito.domain.service.RegistroVeiculoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;

    @GetMapping
    public List<Veiculo> listar(){
        return veiculoRepository.findAll();
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<Veiculo> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo cadastrar(@RequestBody Veiculo veiculo){
        return registroVeiculoService.cadastrar(veiculo);
    }

    @ExceptionHandler(NegocioException.class) // método para ser capaz de tratar exceção
    public ResponseEntity<String> capturar(NegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
