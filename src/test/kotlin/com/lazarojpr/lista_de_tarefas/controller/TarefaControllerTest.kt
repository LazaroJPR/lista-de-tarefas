package com.lazarojpr.lista_de_tarefas.controller

import com.lazarojpr.lista_de_tarefas.repository.TarefaRepository
import com.lazarojpr.lista_de_tarefas.service.TarefaService
import com.lazarojpr.lista_de_tarefas.model.Tarefa
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
class TarefaControllerTest {

    private val tarefaRepository = mock(TarefaRepository::class.java)
    private val tarefaService = TarefaService(tarefaRepository)

    @Test
    fun `deve listar todas as tarefas ordenadas pela ordem de apresentacao`() {
        val tarefas = listOf(
            Tarefa(1, "Tarefa 1", BigDecimal(500), LocalDate.now().plusDays(10), 2),
            Tarefa(2, "Tarefa 2", BigDecimal(1000), LocalDate.now().plusDays(5), 1)
        )

        `when`(tarefaRepository.findAll()).thenReturn(tarefas)

        val resultado = tarefaService.listarTarefas()

        assertEquals(2, resultado.size)
        assertEquals("Tarefa 2", resultado[0].nome)
        assertEquals("Tarefa 1", resultado[1].nome)
    }

    @Test
    fun `deve lançar exceção ao tentar salvar tarefa com nome duplicado`() {
        val tarefaExistente = Tarefa(1, "Tarefa Existente", BigDecimal(500), LocalDate.now(), 1)
        `when`(tarefaRepository.findByNome("Tarefa Existente")).thenReturn(tarefaExistente)

        val novaTarefa = Tarefa(0, "Tarefa Existente", BigDecimal(600), LocalDate.now(), 2)

        val excecao = assertThrows(IllegalArgumentException::class.java) {
            tarefaService.salvarTarefa(novaTarefa)
        }

        assertEquals("Já existe uma tarefa com esse nome.", excecao.message)
    }
}