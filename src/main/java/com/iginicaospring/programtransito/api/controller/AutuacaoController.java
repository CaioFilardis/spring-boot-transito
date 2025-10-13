package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.api.assembler.AutuacaoAssembler;
import com.iginicaospring.programtransito.api.model.AutuacaoModel;
import com.iginicaospring.programtransito.api.model.input.AutuacaoInput;
import com.iginicaospring.programtransito.domain.model.Autuacao;
import com.iginicaospring.programtransito.domain.service.RegistroAutuacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class AutuacaoController {

    // injetar
    private final RegistroAutuacaoService registroAutuacaoService;
    private final AutuacaoAssembler autuacaoAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel registrar(@PathVariable Long veiculoId,
                                   @Valid @RequestBody AutuacaoInput autuacaoInput) {
        Autuacao novaAutuacao = autuacaoAssembler.toEntity(autuacaoInput);
        Autuacao autuacaoRegistrada =  registroAutuacaoService
                .registrar(veiculoId, novaAutuacao);

        return autuacaoAssembler.toModel(autuacaoRegistrada); // converte em uma nova instancia do representantionModel
    }
}
