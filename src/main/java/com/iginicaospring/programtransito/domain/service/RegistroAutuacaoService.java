package com.iginicaospring.programtransito.domain.service;

import com.iginicaospring.programtransito.domain.model.Autuacao;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service // indica que é um compontente do spring
public class RegistroAutuacaoService {

    private RegistroVeiculoService registroVeiculoService;

    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novaAutuacao) {
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId); // vai buscar um veículo ID
        return veiculo.adicionarAutuacao(novaAutuacao);
    }
}
