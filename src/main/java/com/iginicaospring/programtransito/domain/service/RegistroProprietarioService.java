package com.iginicaospring.programtransito.domain.service;

import com.iginicaospring.programtransito.domain.exception.NegocioException;
import com.iginicaospring.programtransito.domain.model.Proprietario;
import com.iginicaospring.programtransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// classe que irá tratar do CRUD, a gestão dos registros.
// @Component // cria uma instância disponível
@AllArgsConstructor // gera um construtor
@Service // semanticamente mais legível(recomendado)
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository; // injetar o repository proprietario

    @Transactional // declarando que o método deve ser executado dentro da transação, deve ser do springboot
    public Proprietario salvar(Proprietario proprietario) {
        // adicionar regras de negócio - não permitir duplicidade
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario)) // aplica um filtro
                .isPresent(); // diz se tem alguma coisa dentro do Optional


        if (emailEmUso) {
            throw new NegocioException("Já existe um proprietario cadastrado");
        }

        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long proprietarioId) {
        proprietarioRepository.deleteById(proprietarioId); // exclui o proprietario pegando seu id
    }
}
