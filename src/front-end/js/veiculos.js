$(function() {
    function listarProprietarios() {
        $.get("http://localhost:8081/proprietarios", function(data) {
            const select = $("#proprietarioId").empty().append('<option value="">Selecione o proprietário</option>');
            data.forEach(function(p) {
                select.append(`<option value="${p.id}">${p.nome}</option>`);
            });
        });
    }

    function listarVeiculos() {
        $.get("http://localhost:8081/veiculos", function(data) {
            let tbody = $("#veiculos-tbody").empty();
            data.forEach(function(v) {
                tbody.append(`
        <tr>
          <td>${v.marca}</td>
          <td>${v.modelo}</td>
          <td>${v.placa}</td>
          <td>${v.statusVeiculo}</td>
          <td>${v.proprietario.nome}</td>
          <td>${v.dataCadastro ? new Date(v.dataCadastro).toLocaleString('pt-BR') : ''}</td>
          <td>${v.dataApreensao ? new Date(v.dataApreensao).toLocaleString('pt-BR') : ''}</td>
          <td>
            ${v.statusVeiculo === "APREENDIDO"
                    ? `<button class="btn btn-sm btn-success btn-remover-apreensao" data-id="${v.id}">Remover Apreensão</button>`
                    : `<button class="btn btn-sm btn-warning btn-apreender" data-id="${v.id}">Apreender</button>`
                }
            <button class="btn btn-sm btn-info btn-editar" data-id="${v.id}"
              data-marca="${v.marca}" data-modelo="${v.modelo}"
              data-placa="${v.placa}" data-proprietario="${v.proprietario.id}">Editar</button>
            <button class="btn btn-sm btn-danger btn-excluir" data-id="${v.id}">Excluir</button>
          </td>
        </tr>
      `);
            });
        });
    }

    listarProprietarios();
    listarVeiculos();

    // Cadastro de veículo
    $("#form-veiculo").on("submit", function(e) {
        e.preventDefault();
        const novo = {
            marca: $("#marca").val(),
            modelo: $("#modelo").val(),
            placa: $("#placa").val(),
            proprietario: { id: $("#proprietarioId").val() }
        };
        $.ajax({
            url: "http://localhost:8081/veiculos",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(novo),
            success: function() {
                listarVeiculos();
                $("#form-veiculo")[0].reset();
            }
        });
    });

    $(document).on("click", ".btn-apreender", function() {
        const id = $(this).data("id");
        $.ajax({
            url: `http://localhost:8081/veiculos/${id}/apreensao`,
            type: "PUT",
            success: listarVeiculos
        });
    });

    $(document).on("click", ".btn-remover-apreensao", function() {
        const id = $(this).data("id");
        $.ajax({
            url: `http://localhost:8081/veiculos/${id}/apreensao`,
            type: "DELETE",
            success: listarVeiculos
        });
    });

    // Botão Excluir
    $(document).on("click", ".btn-excluir", function() {
        const id = $(this).data("id");
        if (confirm("Tem certeza que deseja excluir este veículo?")) {
            $.ajax({
                url: `http://localhost:8081/veiculos/${id}`,
                type: "DELETE",
                success: listarVeiculos
            });
        }
    });

    // Botão Editar
    $(document).on("click", ".btn-editar", function() {
        const id = $(this).data("id");
        const marcaAtual = $(this).data("marca");
        const modeloAtual = $(this).data("modelo");
        const placaAtual = $(this).data("placa");
        const proprietarioAtual = $(this).data("proprietario");

        const marca = prompt("Atualize a marca:", marcaAtual) || marcaAtual;
        const modelo = prompt("Atualize o modelo:", modeloAtual) || modeloAtual;
        const placa = prompt("Atualize a placa:", placaAtual) || placaAtual;
        // Para proprietário, pode criar um select/modal depois, ou manter igual
        const proprietarioId = proprietarioAtual;

        const editado = { marca, modelo, placa, proprietario: { id: proprietarioId } };

        $.ajax({
            url: `http://localhost:8081/veiculos/${id}`,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(editado),
            success: listarVeiculos
        });
    });

});
