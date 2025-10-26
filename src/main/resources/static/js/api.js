/ Função showAlert pré-requisito (já fornecida)
function showAlert(tipo, mensagem) {
    const cor = tipo === "success" ? "success"
        : tipo === "danger" ? "danger"
            : tipo === "warning" ? "warning" : "info";
    $("#alertaApp").html(`
    <div class="alert alert-${cor} alert-dismissible fade show mt-2" role="alert">
      ${mensagem}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  `);
    setTimeout(() => $("#alertaApp").empty(), 4000);
}

// API AJAX genérico — usando showAlert em erros:
function apiGet(url, cb) {
    $.get(url, cb).fail(function(xhr) {
        showAlert("danger", "Erro ao buscar dados!<br>" + (xhr.responseText || ''));
    });
}

function apiPost(url, data, cb) {
    $.ajax({
        url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: cb,
        error: function(xhr) {
            showAlert("danger", "Erro ao salvar!<br>" + (xhr.responseText || ''));
        }
    });
}

function apiPut(url, data, cb) {
    $.ajax({
        url,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: cb,
        error: function(xhr) {
            showAlert("danger", "Erro ao atualizar!<br>" + (xhr.responseText || ''));
        }
    });
}

function apiDelete(url, cb) {
    $.ajax({
        url,
        type: "DELETE",
        success: cb,
        error: function(xhr) {
            showAlert("danger", "Erro ao deletar!<br>" + (xhr.responseText || ''));
        }
    });
}