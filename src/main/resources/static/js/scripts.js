function showCreateModal() {
    document.getElementById('createModal').style.display = 'block';
}

function closeCreateModal() {
    document.getElementById('createModal').style.display = 'none';
}

function createTarefa() {
    const form = document.getElementById('tarefaForm');
    const formData = new FormData(form);

    const tarefa = {
        nome: formData.get('nome'),
        custo: parseFloat(formData.get('custo')), // Converte para número
        dataLimite: formData.get('dataLimite'),
        // Os outros campos serão gerados automaticamente no backend
    };

    fetch('/tarefas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(tarefa),
    })
    .then(response => {
        if (response.ok) {
            alert('Tarefa criada com sucesso!');
            form.reset(); // Limpa o formulário após o envio
        } else {
            return response.json().then(error => {
                throw new Error(error.message || 'Erro ao criar a tarefa.');
            });
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro: ' + error.message); // Mostra o erro na tela
    });
}

function deleteTarefa(id) {
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
