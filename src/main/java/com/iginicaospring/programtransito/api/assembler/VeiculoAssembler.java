package com.iginicaospring.programtransito.api.assembler;


import com.iginicaospring.programtransito.api.model.VeiculoModel;
import com.iginicaospring.programtransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component // indica que são componentes
public class VeiculoAssembler {

    private final ModelMapper modelMapper;

    public VeiculoModel toModel(Veiculo veiculo) {
        return modelMapper.map(veiculo, VeiculoModel.class); // recebe o argumento e substitui pelo VeiculoMdel
    }

    public List<VeiculoModel> toColletionModel(List<Veiculo> veiculos) {
        return veiculos.stream()
                .map(this::toModel) // retorna uma stream de veículo model
                .toList();
    }

}
