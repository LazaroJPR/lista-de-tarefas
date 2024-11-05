document.addEventListener('DOMContentLoaded', () => {
        function deleteTarefa(id) {
        Swal.fire({
            title: 'Você tem certeza?',
            text: 'Esta tarefa será excluída!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim',
            cancelButtonText: 'Não'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`/tarefas/${id}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Erro',
                            text: 'Erro ao excluir tarefa!',
                        });
                    }
                })
                .catch(error => {
                    console.error('Erro:', error);
                });
            }
        });
    }

    function createTarefa(event) {
        event.preventDefault();

        // Verifica se os elementos do formulário existem
        const nome = document.getElementById('nome');
        const custo = document.getElementById('custo');
        const dataLimite = document.getElementById('dataLimite');

        if (nome && custo && dataLimite) {
            const tarefa = {
                nome: nome.value,
                custo: parseFloat(custo.value),
                dataLimite: dataLimite.value
            };

            fetch('/tarefas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(tarefa)
            })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/tarefas/home';
                } else {
                    return response.text().then(text => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Erro',
                            text: text,
                        });
                    });
                }
            })
            .catch(error => {
                console.error('Erro:', error);
            });
        }
    }

    function editTarefa(id, nomeAtual, custoAtual, dataLimiteAtual) {
        Swal.fire({
            title: 'Editar Tarefa',
            html: `
                <input id="nome" class="swal2-input" placeholder="Nome da Tarefa" value="${nomeAtual}">
                <input id="custo" type="number" class="swal2-input" placeholder="Custo (R$)" value="${custoAtual}">
                <input id="dataLimite" type="date" class="swal2-input" placeholder="Data Limite" value="${dataLimiteAtual}">
            `,
            focusConfirm: false,
            showCancelButton: true,
            confirmButtonText: 'Salvar',
            cancelButtonText: 'Cancelar',
            preConfirm: () => {
                const nome = document.getElementById('nome').value;
                const custo = parseFloat(document.getElementById('custo').value);
                const dataLimite = document.getElementById('dataLimite').value;

                // Verifica se os campos estão preenchidos
                if (!nome || isNaN(custo) || !dataLimite) {
                    Swal.showValidationMessage('Por favor, preencha todos os campos');
                    return false;
                }

                return { id, nome, custo, dataLimite };
            }
        }).then((result) => {
            if (result.isConfirmed) {
                const tarefa = {
                    nome: result.value.nome,
                    custo: result.value.custo,
                    dataLimite: result.value.dataLimite
                };

                fetch(`/tarefas/${id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(tarefa)
                })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/tarefas/home';
                    } else {
                        return response.text().then(text => {
                            Swal.fire({
                                icon: 'error',
                                title: 'Erro',
                                text: text,
                            });
                        });
                    }
                })
                .catch(error => {
                    console.error('Erro:', error);
                });
            }
        });
    }

     document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.dataset.id;
            const nome = this.dataset.nome;
            const custo = this.dataset.custo;
            const dataLimite = this.dataset.datalimite;
            editTarefa(id, nome, custo, dataLimite);
        });
    });

    window.deleteTarefa = deleteTarefa;
    window.editTarefa = editTarefa;
    window.createTarefa = createTarefa;
});
