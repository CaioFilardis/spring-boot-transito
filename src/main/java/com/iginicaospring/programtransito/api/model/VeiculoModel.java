package com.iginicaospring.programtransito.api.model;

// modelo de representação da api, apenas para transferência de dados

import com.iginicaospring.programtransito.domain.model.StatusVeiculo;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class VeiculoModel {

    private Long id;
    private String nomeProprietario;
    private String marca;
    private String modelo;
    private String placa;
    private StatusVeiculo statusVeiculo;
    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataApreensao;
}
