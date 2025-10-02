package com.iginicaospring.programtransito.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // anotação com base no lombook instalado
@Entity // Entity = anotação que defina a classe como entidade

public class Proprietario {

    @EqualsAndHashCode.Include // anotação que inclui a propriedade "Id"
    @Id // Id = anotação que define a propriedade de identificação(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // anotação que define a estratégia nativa do banco de dados(ele mesmo faz)
    private Long id;

    @NotBlank // validação dizendo que o nome não pode ser branco
    // @NotNull // validação dizendo que o valor da propriedade não pode ser nulo
    @Size(max = 60) // define um limite de caracteres
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Email // valida se é um e-mail mesmo
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "telefone")
    private String telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    // Hash Code e Equals - Para comparar os objetivos



}
