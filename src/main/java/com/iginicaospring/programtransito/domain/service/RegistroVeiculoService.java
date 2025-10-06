package com.iginicaospring.programtransito.domain.service;

import com.iginicaospring.programtransito.domain.exception.NegocioException;
import com.iginicaospring.programtransito.domain.model.Proprietario;
import com.iginicaospring.programtransito.domain.model.StatusVeiculo;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import com.iginicaospring.programtransito.domain.repository.ProprietarioRepository;
import com.iginicaospring.programtransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioRepository proprietarioRepository;
    private final RegistroProprietarioService registroProprietarioService;

    // método para cadastrar um veículo
    @Transactional // anotação do pacote Spring
    public Veiculo cadastrar(Veiculo novoVeiculo) {
        // evitar passar um veículo pré-existente
        if (novoVeiculo.getId() != null) {
            throw new NegocioException("Veiculo a ser cadastrado");
        }

        // verificador
        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                        .filter(veiculo -> !veiculo.equals(novoVeiculo))
                        .isPresent();

        if (placaEmUso) {
            throw new NegocioException("Já existe um veículo cadastrado com este placa");
        }

        Proprietario proprietario = registroProprietarioService.buscar(novoVeiculo.getId());
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(LocalDateTime.now());

        return veiculoRepository.save(novoVeiculo); // retorna o conteúdo do método
    }
}
