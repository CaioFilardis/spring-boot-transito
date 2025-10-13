package com.iginicaospring.programtransito.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;

// Define quais são as propriedades de entrada que o cliente pode usar
@Getter
@Setter
public class VeiculoInput {

    @NotBlank
    @Size(max = 20)
    private String marca;

    @NotBlank
    @Size(max = 20)
    private String modelo;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}") // validar um padrão_3 letras de A a Z_o formato da placa
    private String placa;
//  private Long proprietarioId;

    @Valid // indica com o próprio ID do proprietário
    @NotNull
    private ProprietarioIdInput proprietario;
}
