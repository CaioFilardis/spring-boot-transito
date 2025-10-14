package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.api.assembler.VeiculoAssembler;
import com.iginicaospring.programtransito.api.model.VeiculoModel;
import com.iginicaospring.programtransito.api.model.input.VeiculoInput;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import com.iginicaospring.programtransito.domain.repository.VeiculoRepository;
import com.iginicaospring.programtransito.domain.service.ApreensaoVeiculosService;
import com.iginicaospring.programtransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
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
    private final ApreensaoVeiculosService apreensaoVeiculosService;
    private final VeiculoAssembler veiculoAssembler;

    @GetMapping
    public List<VeiculoModel> listar(){
        return veiculoAssembler.toColletionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId){ // retorna a instabcia do modelo de compilação
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody VeiculoInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput); // pegamos o input
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo); // cadastra o que recebe do input

        return veiculoAssembler.toModel(veiculoCadastrado); // retorna esses dados a salvar no banco
    }

    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId) {
        apreensaoVeiculosService.apreender(veiculoId);
    }

    @DeleteMapping("/{veiculoId}/apreensao") // muda apenas o verbo
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId) {
        apreensaoVeiculosService.removerApreensao(veiculoId);
    }
}
