package com.iginicaospring.programtransito.domain.service;

import com.iginicaospring.programtransito.domain.model.StatusVeiculo;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import com.iginicaospring.programtransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private VeiculoRepository veiculoRepository;

    // método para cadastrar um veículo
    @Transactional // anotação do pacote Spring
    public Veiculo cadastrar(Veiculo novoVeiculo) {


        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(LocalDateTime.now());

        return veiculoRepository.save(novoVeiculo); // retorna o conteúdo do método
    }
}
