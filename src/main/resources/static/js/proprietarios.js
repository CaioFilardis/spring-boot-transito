$(function() {
    function listarProprietarios() {
        apiGet("http://localhost:8081/proprietarios", function(data) {
            let tbody = $("#proprietarios-tbody").empty();
            data.forEach(function(p) {
                tbody.append(`
          <tr>
            <td>${p.nome}</td>
            <td>${p.email}</td>
            <td>${p.telefone}</td>
            <td>
              <button class="btn btn-sm btn-warning btn-editar" data-id="${p.id}" data-nome="${p.nome}" data-email="${p.email}" data-telefone="${p.telefone}">Editar</button>
              <button class="btn btn-sm btn-danger btn-remover" data-id="${p.id}">Remover</button>
            </td>
          </tr>
        `);
            });
        });
    }

    listarProprietarios();

    // Cadastro
    $("#form-proprietario").on("submit", function(e) {
        e.preventDefault();
        const nome = $("#nome").val().trim();
        const email = $("#email").val().trim();
        const telefone = $("#telefone").val().trim();

        if (!nome || !email || !telefone) {
            showAlert("warning", "Preencha todos os campos!");
            return;
        }
        const emailOk = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        if (!emailOk) {
            showAlert("danger", "E-mail inválido.");
            return;
        }
        apiPost("http://localhost:8081/proprietarios", {nome, email, telefone}, function() {
            showAlert("success", "Proprietário cadastrado com sucesso!");
            listarProprietarios();
            $("#form-proprietario")[0].reset();
        });
    });

    // Editar
    $(document).on("click", ".btn-editar", function() {
        const id = $(this).data("id");
        const nome = prompt("Novo nome:", $(this).data("nome"));
        const email = prompt("Novo email:", $(this).data("email"));
        const telefone = prompt("Novo telefone:", $(this).data("telefone"));
        if (nome && email && telefone) {
            apiPut("http://localhost:8081/proprietarios/" + id, {nome, email, telefone}, listarProprietarios);
        }
    });

    // Remover
    $(document).on("click", ".btn-remover", function() {
        const id = $(this).data("id");
        apiDelete("http://localhost:8081/proprietarios/" + id, function() {
            showAlert("success", "Proprietário removido!");
            listarProprietarios();
        });
    });

});
