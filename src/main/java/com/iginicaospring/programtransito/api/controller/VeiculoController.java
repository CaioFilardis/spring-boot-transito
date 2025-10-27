package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.api.assembler.ProprietarioAssembler;
import com.iginicaospring.programtransito.api.assembler.VeiculoAssembler;
import com.iginicaospring.programtransito.api.model.VeiculoModel;
import com.iginicaospring.programtransito.api.model.input.VeiculoInput;
import com.iginicaospring.programtransito.domain.model.Proprietario;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import com.iginicaospring.programtransito.domain.repository.ProprietarioRepository;
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
    private final ProprietarioAssembler proprietarioAssembler;

    @GetMapping
    public List<VeiculoModel> listar(){
        return veiculoAssembler.toColletionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody VeiculoInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput);
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo);

        return veiculoAssembler.toModel(veiculoCadastrado);
    }

    @PutMapping("/{veiculoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long veiculoId, @Valid @RequestBody VeiculoInput veiculoInput){
        // 1. buscar veículo original
        Veiculo veiculoExistente = veiculoRepository.findById(veiculoId).orElse(null);
        if (veiculoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        // 2. atualizar campos editáveis
        veiculoExistente.setMarca(veiculoInput.getMarca());
        veiculoExistente.setModelo(veiculoInput.getModelo());
        veiculoExistente.setPlaca(veiculoInput.getPlaca());

        if (veiculoInput.getProprietario() != null) {
            Proprietario proprietario = proprietarioAssembler.toEntity(veiculoInput.getProprietario());
            veiculoExistente.setProprietario(proprietario);
        }

        Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);
        return ResponseEntity.ok(veiculoAssembler.toModel(veiculoAtualizado));
    }

    @DeleteMapping("/{veiculoId}")
    public ResponseEntity<Void> excluir(@PathVariable Long veiculoId) {
        if (!veiculoRepository.existsById(veiculoId)) {
            return ResponseEntity.notFound().build();
        }
        veiculoRepository.deleteById(veiculoId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId) {
        apreensaoVeiculosService.apreender(veiculoId);
    }

    @DeleteMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId) {
        apreensaoVeiculosService.removerApreensao(veiculoId);
    }
}
