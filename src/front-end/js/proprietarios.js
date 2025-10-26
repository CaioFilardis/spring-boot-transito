$(function() {
    function listarProprietarios() {
        apiGet("http://localhost:8081/proprietarios", function(data) {
            let tbody = $("#proprietarios-tbody").empty();
            data.forEach(function(p) {
                tbody.append(`
          <tr>
            <td>${p.nome}</td>
            <td>${p.email}</td>
            <td>
              <button class="btn btn-sm btn-warning btn-editar" data-id="${p.id}" data-nome="${p.nome}" data-email="${p.email}">Editar</button>
              <button class="btn btn-sm btn-danger btn-remover" data-id="${p.id}">Remover</button>
            </td>
          </tr>
        `);
            });
        });
    }

    listarProprietarios();

    // Adicionar
    $("#form-proprietario").on("submit", function(e) {
        e.preventDefault();
        const novo = {
            nome: $("#nome").val(),
            email: $("#email").val()
        };
        apiPost("http://localhost:8081/proprietarios", novo, function() {
            listarProprietarios();
            $("#form-proprietario")[0].reset();
        });
    });

    // Remover
    $(document).on("click", ".btn-remover", function() {
        const id = $(this).data("id");
        apiDelete("http://localhost:8081/proprietarios/" + id, listarProprietarios);
    });

    // Editar
    $(document).on("click", ".btn-editar", function() {
        const id = $(this).data("id");
        const nome = prompt("Novo nome:", $(this).data("nome"));
        const email = prompt("Novo email:", $(this).data("email"));
        if (nome && email) {
            apiPut("http://localhost:8081/proprietarios/" + id, {nome, email}, listarProprietarios);
        }
    });
});
