function showCreateModal() {
    document.getElementById('createModal').style.display = 'block';
}

function closeCreateModal() {
    document.getElementById('createModal').style.display = 'none';
}

function createTarefa(event) {
    event.preventDefault(); // Evita o envio do formulário padrão

    // Coleta os dados do formulário
    const nome = document.getElementById('nome').value;
    const custo = document.getElementById('custo').value;
    const dataLimite = document.getElementById('dataLimite').value;

    // Cria um objeto para enviar ao servidor
    const tarefa = {
        nome: nome,
        custo: parseFloat(custo), // Converte para número
        dataLimite: dataLimite
    };

    // Faz a requisição POST para criar a nova tarefa
    fetch('/tarefas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tarefa)
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/tarefas/home'; // Redireciona após a criação
        } else {
            return response.text().then(text => { alert(text); });
        }
    })
    .catch(error => {
        console.error('Erro:', error); // Log de erro no console
    });
}

function deleteTarefa(id) {
    console.log("ID da tarefa para deletar:", id);
    if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
        fetch(`/tarefas/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Erro ao excluir tarefa!');
            }
        });
    }
}

function editTarefa(id) {
    // Aqui você pode abrir um modal ou redirecionar para uma página de edição
    // Para simplificar, vamos apenas alertar
    alert('Função de edição não implementada ainda.');
}
