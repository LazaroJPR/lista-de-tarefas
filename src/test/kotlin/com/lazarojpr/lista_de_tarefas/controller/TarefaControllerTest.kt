package com.lazarojpr.lista_de_tarefas.controller

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.service.TarefaService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
class TarefaControllerTest {

    private lateinit var tarefaController: TarefaController

    @Mock
    private lateinit var tarefaService: TarefaService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        tarefaController = TarefaController(tarefaService)
    }

    @Test
    fun `deve criar uma tarefa`() {
        val tarefa = Tarefa(
            id = 1L,
            nome = "Tarefa Exemplo",
            custo = BigDecimal(100),
            dataLimite = LocalDate.of(2024, 12, 31),
            ordemApresentacao = 1
        )

        Mockito.`when`(tarefaService.salvarTarefa(tarefa)).thenReturn(tarefa)
        tarefaController.criarTarefa(tarefa)
        Mockito.verify(tarefaService, Mockito.times(1)).salvarTarefa(tarefa)
    }

    @Test
    fun `deve atualizar uma tarefa`() {
        val tarefa = Tarefa(
            id = 1L,
            nome = "Tarefa Atualizada",
            custo = BigDecimal(200),
            dataLimite = LocalDate.of(2024, 11, 30),
            ordemApresentacao = 2
        )

        val id = tarefa.id

        Mockito.`when`(tarefaService.atualizarTarefa(id, tarefa)).thenReturn(tarefa)
        tarefaController.atualizarTarefa(id, tarefa)
        Mockito.verify(tarefaService, Mockito.times(1)).atualizarTarefa(id, tarefa)
    }

    @Test
    fun `deve excluir uma tarefa`() {
        val id = 1L

        tarefaController.excluirTarefa(id)
        Mockito.verify(tarefaService, Mockito.times(1)).excluirTarefa(id)
    }

    @Test
    fun `deve listar todas as tarefas`() {
        val tarefas = listOf(
            Tarefa(
                id = 1L,
                nome = "Tarefa Exemplo",
                custo = BigDecimal(100),
                dataLimite = LocalDate.of(2024, 12, 31),
                ordemApresentacao = 1
            )
        )

        Mockito.`when`(tarefaService.listarTarefas()).thenReturn(tarefas)
        tarefaController.listarTarefas()
        Mockito.verify(tarefaService, Mockito.times(1)).listarTarefas()
    }
}
