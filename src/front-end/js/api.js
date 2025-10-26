// Função genérica para GET
function apiGet(url, cb) {
    $.get(url, cb).fail(function(xhr) {
        alert("Erro ao buscar dados!\n" + (xhr.responseText || ''));
    });
}

// Função genérica para POST
function apiPost(url, data, cb) {
    $.ajax({
        url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: cb,
        error: function(xhr) {
            alert("Erro ao salvar!\n" + (xhr.responseText || ''));
        }
    });
}

// PUT genérico
function apiPut(url, data, cb) {
    $.ajax({
        url,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: cb,
        error: function(xhr) {
            alert("Erro ao atualizar!\n" + (xhr.responseText || ''));
        }
    });
}

// DELETE genérico
function apiDelete(url, cb) {
    $.ajax({
        url,
        type: "DELETE",
        success: cb,
        error: function(xhr) {
            alert("Erro ao deletar!\n" + (xhr.responseText || ''));
        }
    });
}
