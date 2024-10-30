package com.lazarojpr.lista_de_tarefas.service

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.repository.TarefaRepository
import org.springframework.stereotype.Service

@Service
class TarefaService(private val tarefaRepository: TarefaRepository) {

    fun listarTarefas(): List<Tarefa> = tarefaRepository.findAll().sortedBy { it.ordemApresentacao }

    fun salvarTarefa(tarefa: Tarefa): Tarefa {
        if (tarefaRepository.findByNome(tarefa.nome) != null) {
            throw IllegalArgumentException("Já existe uma tarefa com esse nome.")
        }
        return tarefaRepository.save(tarefa)
    }

    fun atualizarTarefa(id: Long, tarefaAtualizada: Tarefa): Tarefa {
        val tarefaExistente = tarefaRepository.findById(id).orElseThrow { IllegalArgumentException("Tarefa não encontrada") }
        if (tarefaRepository.findByNome(tarefaAtualizada.nome) != null && tarefaAtualizada.nome != tarefaExistente.nome) {
            throw IllegalArgumentException("Já existe uma tarefa com esse nome.")
        }
        val tarefaComAtualizacoes = tarefaExistente.copy(
            nome = tarefaAtualizada.nome,
            custo = tarefaAtualizada.custo,
            dataLimite = tarefaAtualizada.dataLimite
        )
        return tarefaRepository.save(tarefaComAtualizacoes)
    }

    fun excluirTarefa(id: Long) = tarefaRepository.deleteById(id)
}
