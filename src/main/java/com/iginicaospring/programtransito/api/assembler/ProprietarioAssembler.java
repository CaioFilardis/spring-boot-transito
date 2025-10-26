package com.iginicaospring.programtransito.api.assembler;

import com.iginicaospring.programtransito.api.model.input.ProprietarioIdInput;
import com.iginicaospring.programtransito.domain.model.Proprietario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProprietarioAssembler {
    public Proprietario toEntity(ProprietarioIdInput input) {
        Proprietario prop = new Proprietario();
        prop.setId(input.getId());
        // outros campos, se houver
        return prop;
    }
}
