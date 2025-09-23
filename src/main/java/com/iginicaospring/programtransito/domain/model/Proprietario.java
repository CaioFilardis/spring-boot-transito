package com.iginicaospring.programtransito.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // anotação com base no lombook instalado
@Entity // Entity = anotação que defina a classe como entidade
// @Table(name = "tb_proprietario") // renomear a entidade se desejável
public class Proprietario {

    @EqualsAndHashCode.Include // anotação que inclui a propriedade "Id"
    @Id // Id = anotação que define a propriedade de identificação(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // anotação que define a estratégia nativa do banco de dados(ele mesmo faz)
    private Long id;

    private String nome;
    private String email;

    @Column(name = "celular") // anotação que mapea exatamente a coluna deseja e renomea
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
