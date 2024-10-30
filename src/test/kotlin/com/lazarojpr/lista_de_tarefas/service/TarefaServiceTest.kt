package com.lazarojpr.lista_de_tarefas.service

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.repository.TarefaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@SpringBootTest
class TarefaServiceTest {

    private val tarefaRepository: TarefaRepository = mock(TarefaRepository::class.java)
    private val tarefaService: TarefaService = TarefaService(tarefaRepository)

    private val tarefaMock = Tarefa(
        id = 1L,
        nome = "Tarefa Teste",
        custo = BigDecimal.valueOf(100),
        dataLimite = LocalDate.of(2024, 12, 31),
        ordemApresentacao = 1
    )

    @Test
    fun `listarTarefas deve retornar lista ordenada de tarefas`() {
        `when`(tarefaRepository.findAll()).thenReturn(listOf(tarefaMock))

        val result = tarefaService.listarTarefas()

        assertEquals(1, result.size)
        assertEquals(tarefaMock.nome, result[0].nome)
    }

    @Test
    fun `salvarTarefa deve salvar tarefa quando nome não existe`() {
        `when`(tarefaRepository.findByNome(tarefaMock.nome)).thenReturn(null)
        `when`(tarefaRepository.save(tarefaMock)).thenReturn(tarefaMock)

        val result = tarefaService.salvarTarefa(tarefaMock)

        assertEquals(tarefaMock.nome, result.nome)
        verify(tarefaRepository, times(1)).save(tarefaMock)
    }

    @Test
    fun `salvarTarefa deve lançar exceção quando nome já existe`() {
        `when`(tarefaRepository.findByNome(tarefaMock.nome)).thenReturn(tarefaMock)

        val exception = assertThrows<IllegalArgumentException> {
            tarefaService.salvarTarefa(tarefaMock)
        }

        assertEquals("Já existe uma tarefa com esse nome.", exception.message)
    }

    @Test
    fun `atualizarTarefa deve atualizar tarefa existente`() {
        val tarefaAtualizada = tarefaMock.copy(nome = "Tarefa Atualizada")
        `when`(tarefaRepository.findById(tarefaMock.id)).thenReturn(Optional.of(tarefaMock))
        `when`(tarefaRepository.findByNome(tarefaAtualizada.nome)).thenReturn(null)
        `when`(tarefaRepository.save(any(Tarefa::class.java))).thenReturn(tarefaAtualizada)

        val result = tarefaService.atualizarTarefa(tarefaMock.id, tarefaAtualizada)

        assertEquals(tarefaAtualizada.nome, result.nome)
        verify(tarefaRepository).save(any(Tarefa::class.java))
    }

    @Test
    fun `atualizarTarefa deve lançar exceção quando nome já existe em outra tarefa`() {
        val tarefaAtualizada = tarefaMock.copy(nome = "Outro Nome")
        val outraTarefa = Tarefa(2L, "Outro Nome", BigDecimal.valueOf(200), LocalDate.of(2024, 12, 31), 2)

        `when`(tarefaRepository.findById(tarefaMock.id)).thenReturn(Optional.of(tarefaMock))
        `when`(tarefaRepository.findByNome(outraTarefa.nome)).thenReturn(outraTarefa)

        val exception = assertThrows<IllegalArgumentException> {
            tarefaService.atualizarTarefa(tarefaMock.id, tarefaAtualizada)
        }

        assertEquals("Já existe uma tarefa com esse nome.", exception.message)
    }

    @Test
    fun `excluirTarefa deve excluir tarefa por id`() {
        doNothing().`when`(tarefaRepository).deleteById(tarefaMock.id)

        tarefaService.excluirTarefa(tarefaMock.id)
        verify(tarefaRepository, times(1)).deleteById(tarefaMock.id)
    }
}
