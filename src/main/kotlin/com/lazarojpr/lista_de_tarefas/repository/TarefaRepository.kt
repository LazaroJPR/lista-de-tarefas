package com.lazarojpr.lista_de_tarefas.repository

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TarefaRepository : JpaRepository<Tarefa, Long>{
    fun findByNome(nome: String): Tarefa?

    @Query("SELECT MAX(t.ordemApresentacao) FROM Tarefa t")
    fun findMaxOrdemApresentacao(): Int?
}
