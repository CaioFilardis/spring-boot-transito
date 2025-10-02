package com.iginicaospring.programtransito.domain.repository;

import com.iginicaospring.programtransito.domain.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// interface que fornece métodos que acessa os dados do proprietário
@Repository // anotação para dizer que ao mapeamento que isto é um repositório
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long>  {

    List<Proprietario> findByNome(String nome);
    Optional<Proprietario> findByEmail(String email); // faz o filtro, pode ou não ter o email
}
