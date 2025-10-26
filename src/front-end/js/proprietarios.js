$(function() {
    // Listar todos os proprietários
    function listarProprietarios() {
        $.get("http://localhost:8081/proprietarios", function(data) {
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

    // Adicionar proprietário
    $("#form-proprietario").on("submit", function(e) {
        e.preventDefault();
        const novo = {
            nome: $("#nome").val(),
            email: $("#email").val()
        };
        $.ajax({
            url: "http://localhost:8081/proprietarios",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(novo),
            success: function() {
                listarProprietarios();
                $("#form-proprietario")[0].reset();
            }
        });
    });

    // Remover proprietário
    $(document).on("click", ".btn-remover", function() {
        const id = $(this).data("id");
        $.ajax({
            url: "http://localhost:8081/proprietarios/" + id,
            type: "DELETE",
            success: listarProprietarios
        });
    });

    // Editar proprietário (simples - pode ser aprimorado com modal)
    $(document).on("click", ".btn-editar", function() {
        const id = $(this).data("id");
        const nome = prompt("Novo nome:", $(this).data("nome"));
        const email = prompt("Novo email:", $(this).data("email"));
        if (nome && email) {
            $.ajax({
                url: "http://localhost:8081/proprietarios/" + id,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify({nome, email}),
                success: listarProprietarios
            });
        }
    });
});
