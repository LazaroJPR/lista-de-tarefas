package com.lazarojpr.lista_de_tarefas.service

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.repository.TarefaRepository
import org.springframework.stereotype.Service

@Service
class TarefaService(
    private val tarefaRepository: TarefaRepository,
) {
    fun listarTarefas(): List<Tarefa> = tarefaRepository.findAll().sortedBy { it.ordemApresentacao }

    fun salvarTarefa(tarefa: Tarefa): Tarefa {
        if (tarefaRepository.findByNome(tarefa.nome) != null) {
            throw IllegalArgumentException("Já existe uma tarefa com esse nome.")
        }

        val maxOrdem = tarefaRepository.findMaxOrdemApresentacao() ?: 0 // Retorna 0 se não houver tarefas
        val tarefaComOrdem = tarefa.copy(ordemApresentacao = maxOrdem + 1) // Incrementa para a nova tarefa

        return tarefaRepository.save(tarefaComOrdem)
    }

    fun atualizarTarefa(
        id: Long,
        tarefaAtualizada: Tarefa,
    ): Tarefa {
        val tarefaExistente = tarefaRepository.findById(id).orElseThrow { IllegalArgumentException("Tarefa não encontrada") }
        if (tarefaRepository.findByNome(tarefaAtualizada.nome) != null && tarefaAtualizada.nome != tarefaExistente.nome) {
            throw IllegalArgumentException("Já existe uma tarefa com esse nome.")
        }
        val tarefaComAtualizacoes =
            tarefaExistente.copy(
                nome = tarefaAtualizada.nome,
                custo = tarefaAtualizada.custo,
                dataLimite = tarefaAtualizada.dataLimite,
            )
        return tarefaRepository.save(tarefaComAtualizacoes)
    }

    fun excluirTarefa(id: Long) = tarefaRepository.deleteById(id)

    fun trocarTarefas(
        id1: Long,
        id2: Long,
    ) {
        val tarefa1 = tarefaRepository.findById(id1).orElseThrow { IllegalArgumentException("Tarefa $id1 não encontrada") }
        val tarefa2 = tarefaRepository.findById(id2).orElseThrow { IllegalArgumentException("Tarefa $id2 não encontrada") }

        val ordemTemp = tarefa1.ordemApresentacao
        val tarefaAtualizada1 = tarefa1.copy(ordemApresentacao = tarefa2.ordemApresentacao)
        val tarefaAtualizada2 = tarefa2.copy(ordemApresentacao = ordemTemp)

        tarefaRepository.save(tarefaAtualizada1)
        tarefaRepository.save(tarefaAtualizada2)
    }
}
