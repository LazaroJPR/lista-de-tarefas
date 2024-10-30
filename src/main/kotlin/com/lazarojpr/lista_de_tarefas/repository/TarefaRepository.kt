package com.lazarojpr.lista_de_tarefas.repository

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import org.springframework.data.jpa.repository.JpaRepository

interface TarefaRepository : JpaRepository<Tarefa, Long>{
    fun findByNome(nome: String): Tarefa?
}
