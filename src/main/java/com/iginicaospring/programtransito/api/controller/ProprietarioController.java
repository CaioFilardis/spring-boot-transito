package com.iginicaospring.programtransito.api.controller;

import com.iginicaospring.programtransito.domain.model.Proprietarios;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProprietarioController {

    // método para retornar uma lista de proprietários
    @GetMapping("/proprietarios")
    public List<Proprietarios> listar() {
        var proprietario1 = new Proprietarios();
        proprietario1.setId(1L);
        proprietario1.setNome("João");
        proprietario1.setEmail("joão.santos@gmail.com");
        proprietario1.setTelefone("21 99999-9999");

        var proprietario2 = new Proprietarios();
        proprietario2.setId(2L);
        proprietario2.setNome("Maria");
        proprietario2.setEmail("maria.santos@gmail.com");
        proprietario2.setTelefone("22 99999-9999");

        // returnar em forma de arrays, por ser uma lista
        return Arrays.asList(proprietario1, proprietario2);
    }
}
