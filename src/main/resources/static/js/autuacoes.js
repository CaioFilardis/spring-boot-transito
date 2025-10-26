$(function() {
    function listarVeiculos() {
        apiGet("http://localhost:8081/veiculos", function(data) {
            const select = $("#veiculoId").empty().append('<option value="">Escolha...</option>');
            data.forEach(function(v) {
                select.append(`<option value="${v.id}">${v.marca} ${v.modelo} [${v.placa}]</option>`);
            });
        });
    }
    function listarAutuacoes(veiculoId) {
        if (!veiculoId) return $("#autuacoes-tbody").empty();
        apiGet(`http://localhost:8081/veiculos/${veiculoId}/autuacoes`, function(data) {
            let tbody = $("#autuacoes-tbody").empty();
            data.forEach(function(a) {
                tbody.append(`
          <tr>
            <td>${a.descricao || a.detalhe || "Sem descrição"}</td>
            <td>${a.dataOcorrencia ? new Date(a.dataOcorrencia).toLocaleString('pt-BR') : ""}</td>
          </tr>
        `);
            });
        });
    }
    listarVeiculos();

    $("#veiculoId").on("change", function() {
        listarAutuacoes($(this).val());
    });

    $("#form-autuacao").on("submit", function(e) {
        e.preventDefault();
        const veiculoId = $("#veiculoId").val();
        const detalhe = $("#detalhe").val();
        if (!veiculoId) {
            alert("Selecione um veículo!");
            return;
        }
        apiPost(`http://localhost:8081/veiculos/${veiculoId}/autuacoes`, { descricao: detalhe }, function() {
            listarAutuacoes(veiculoId);
            $("#detalhe").val("");
        });
    });

    // alert
    $("#form-autuacao").on("submit", function(e) {
        e.preventDefault();
        const veiculoId = $("#veiculoId").val();
        const detalhe = $("#detalhe").val().trim();

        if (!veiculoId) {
            showAlert("warning", "Selecione um veículo!");
            return;
        }
        if (!detalhe) {
            showAlert("warning", "Informe a descrição da infração!");
            return;
        }

        apiPost(`http://localhost:8081/veiculos/${veiculoId}/autuacoes`, { descricao: detalhe }, function() {
            showAlert("success", "Autuação registrada!");
            listarAutuacoes(veiculoId);
            $("#detalhe").val("");
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

    let idExclui = null;
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
