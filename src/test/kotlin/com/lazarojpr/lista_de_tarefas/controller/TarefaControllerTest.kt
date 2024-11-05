package com.lazarojpr.lista_de_tarefas.controller

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.service.TarefaService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class TarefaControllerTest {
    @Mock
    private lateinit var tarefaService: TarefaService

    @InjectMocks
    private lateinit var tarefaController: TarefaController

    @Test
    fun `deve criar uma tarefa`() {
        val tarefa =
            Tarefa(
                id = 1L,
                nome = "Tarefa Exemplo",
                custo = BigDecimal(100),
                dataLimite = LocalDate.of(2024, 12, 31),
                ordemApresentacao = 1,
            )

        Mockito.`when`(tarefaService.salvarTarefa(tarefa)).thenReturn(tarefa)

        val response = tarefaController.criarTarefa(tarefa)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(tarefa, response.body)
        Mockito.verify(tarefaService, Mockito.times(1)).salvarTarefa(tarefa)
    }

    @Test
    fun `deve atualizar uma tarefa`() {
        val tarefa =
            Tarefa(
                id = 1L,
                nome = "Tarefa Atualizada",
                custo = BigDecimal(200),
                dataLimite = LocalDate.of(2024, 11, 30),
                ordemApresentacao = 2,
            )

        val id = tarefa.id!! // Converte id para Long não nulo
        Mockito.`when`(tarefaService.atualizarTarefa(id, tarefa)).thenReturn(tarefa)

        val response = tarefaController.atualizarTarefa(id, tarefa)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(tarefa, response.body)
        Mockito.verify(tarefaService, Mockito.times(1)).atualizarTarefa(id, tarefa)
    }

    @Test
    fun `deve excluir uma tarefa`() {
        val id = 1L

        val response = tarefaController.excluirTarefa(id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        Mockito.verify(tarefaService, Mockito.times(1)).excluirTarefa(id)
    }

    @Test
    fun `deve listar todas as tarefas`() {
        val tarefas =
            listOf(
                Tarefa(
                    id = 1L,
                    nome = "Tarefa Exemplo",
                    custo = BigDecimal(100),
                    dataLimite = LocalDate.of(2024, 12, 31),
                    ordemApresentacao = 1,
                ),
            )

        Mockito.`when`(tarefaService.listarTarefas()).thenReturn(tarefas)

        val response = tarefaController.listarTarefas()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(tarefas, response.body)
        Mockito.verify(tarefaService, Mockito.times(1)).listarTarefas()
    }

    @Test
    fun `deve trocar duas tarefas`() {
        val id1 = 1L
        val id2 = 2L

        val response = tarefaController.trocarTarefas(id1, id2)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        Mockito.verify(tarefaService, Mockito.times(1)).trocarTarefas(id1, id2)
    }
}
