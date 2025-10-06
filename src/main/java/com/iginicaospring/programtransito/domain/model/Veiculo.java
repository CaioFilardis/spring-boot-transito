package com.iginicaospring.programtransito.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// criando Getters e Settert, Equals e HashCode via Loombok
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity // dizemos ao jakarta que é uma entidade
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // --> Alteração, delegando ao banco de dados, auto-increment
    @EqualsAndHashCode.Include // comparando objetos via id
    private Long id;

    @ManyToOne // anotação que diz que a composição tem uma relação muitos para um
    // @JoinColumn(name = "proprietario_id") // anotação permite especificar o nome da propriedade que aplica o relacionamento
    private Proprietario proprietario;

    private String marca;
    private String modelo;
    private String placa;

    @Enumerated(EnumType.STRING) // possibilita configurar o que queremos relacionar(neste caso, nome)
    private StatusVeiculo status;


    private LocalDateTime dataCadastro; // horário de brasília
    private LocalDateTime dataApreensao;
}
