/* criando a tabela */
create table proprietario (
        id bigint not null auto_increment,
        nome varchar(60) not null,
        email varchar(255) not null,
        telefone varchar(20) not null,

        primary key (id)
);

alter table proprietario /* comando para alterar a tabela selecionada */

add constraint uk_proprietario unique (email) /* comando de restrição */
