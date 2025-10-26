$(function() {
    function listarProprietarios() {
        apiGet("http://localhost:8081/proprietarios", function(data) {
            const select = $("#proprietarioId").empty().append('<option value="">Selecione o proprietário</option>');
            data.forEach(function(p) {
                select.append(`<option value="${p.id}">${p.nome}</option>`);
            });
        });
    }

    function listarVeiculos() {
        apiGet("http://localhost:8081/veiculos", function(data) {
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

    // Cadastro
    $("#form-veiculo").on("submit", function(e) {
        e.preventDefault();
        const novo = {
            marca: $("#marca").val(),
            modelo: $("#modelo").val(),
            placa: $("#placa").val(),
            proprietario: { id: $("#proprietarioId").val() }
        };
        apiPost("http://localhost:8081/veiculos", novo, function() {
            listarVeiculos();
            $("#form-veiculo")[0].reset();
        });
    });

    // Apreender
    $(document).on("click", ".btn-apreender", function() {
        const id = $(this).data("id");
        apiPut(`http://localhost:8081/veiculos/${id}/apreensao`, {}, listarVeiculos);
    });

    // Remover apreensão
    $(document).on("click", ".btn-remover-apreensao", function() {
        const id = $(this).data("id");
        apiDelete(`http://localhost:8081/veiculos/${id}/apreensao`, listarVeiculos);
    });

    // Excluir veículo
    $(document).on("click", ".btn-excluir", function() {
        const id = $(this).data("id");
        if (confirm("Tem certeza que deseja excluir este veículo?")) {
            apiDelete(`http://localhost:8081/veiculos/${id}`, listarVeiculos);
        }
    });

    // Editar veículo
    $(document).on("click", ".btn-editar", function() {
        const id = $(this).data("id");
        const marcaAtual = $(this).data("marca");
        const modeloAtual = $(this).data("modelo");
        const placaAtual = $(this).data("placa");
        const proprietarioAtual = $(this).data("proprietario");

        const marca = prompt("Atualize a marca:", marcaAtual) || marcaAtual;
        const modelo = prompt("Atualize o modelo:", modeloAtual) || modeloAtual;
        const placa = prompt("Atualize a placa:", placaAtual) || placaAtual;
        const proprietarioId = proprietarioAtual;

        const editado = { marca, modelo, placa, proprietario: { id: proprietarioId } };

        apiPut(`http://localhost:8081/veiculos/${id}`, editado, listarVeiculos);
    });


    // alert
    $("#form-veiculo").on("submit", function(e) {
        e.preventDefault();
        const marca = $("#marca").val().trim();
        const modelo = $("#modelo").val().trim();
        const placa = $("#placa").val().trim();
        const proprietarioId = $("#proprietarioId").val();

        if (!marca || !modelo || !placa || !proprietarioId) {
            showAlert("warning", "Preencha todos os campos do veículo, inclusive proprietário!");
            return;
        }
        // Placa: simples validação (exemplo: 3 letras + 4 números)
        const placaOk = /^[A-Z]{3}\d{4}$/i.test(placa);
        if (!placaOk) {
            showAlert("danger", "Placa inválida. Use formato padrão (ABC1234).");
            return;
        }
        const veiculo = {
            marca,
            modelo,
            placa,
            proprietario: { id: proprietarioId }
        };
        apiPost("http://localhost:8081/veiculos", veiculo, function() {
            showAlert("success", "Veículo cadastrado com sucesso!");
            listarVeiculos();
            $("#form-veiculo")[0].reset();
        });
    });

    let idExcluir = null;
    $(document).on("click", ".btn-excluir", function() {
        idExcluir = $(this).data("id");
        $("#mensagemModal").text("Deseja realmente excluir este veículo?");
        var modal = new bootstrap.Modal(document.getElementById('modalConfirma'));
        modal.show();
    });
    $("#btnConfirma").on("click", function() {
        apiDelete(`http://localhost:8081/veiculos/${idExcluir}`, function() {
            showAlert("success", "Veículo excluído!");
            listarVeiculos();
        });
        var modal = bootstrap.Modal.getInstance(document.getElementById('modalConfirma'));
        modal.hide();
    });

});
