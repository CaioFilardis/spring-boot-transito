package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.domain.model.Proprietario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProprietarioController {

    @PersistenceContext // injetando contexto de persistência
    private EntityManager manager; // injetando manager(interface)
    // método para retornar uma lista de proprietários
    @GetMapping("/proprietarios")
    public List<Proprietario> listar() {
        // iniciando operações
        // 1. consulta
        return manager.createQuery("from Proprietario", Proprietario.class).getResultList(); // JPQL, linguagen para consultar objetos da entidade mapeado
    }
}
