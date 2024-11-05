package com.lazarojpr.lista_de_tarefas.repository

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.math.BigDecimal
import java.time.LocalDate

@DataJpaTest
class TarefaRepositoryTest {
    @Autowired
    private lateinit var tarefaRepository: TarefaRepository

    private lateinit var tarefa: Tarefa

    @BeforeEach
    fun setup() {
        tarefaRepository.deleteAll()

        tarefa =
            Tarefa(
                nome = "Estudar Kotlin",
                custo = BigDecimal(50),
                dataLimite = LocalDate.now().plusDays(7),
                ordemApresentacao = 1,
            )
        tarefaRepository.save(tarefa)
    }

    @Test
    fun `deve encontrar tarefa pelo nome`() {
        val tarefaEncontrada = tarefaRepository.findByNome("Estudar Kotlin")
        assertNotNull(tarefaEncontrada)
        assertEquals(tarefa.nome, tarefaEncontrada?.nome)
    }

    @Test
    fun `deve retornar nulo se tarefa nao for encontrada pelo nome`() {
        val tarefaNaoEncontrada = tarefaRepository.findByNome("Nome Inexistente")
        assertNull(tarefaNaoEncontrada)
    }

    @Test
    fun `deve salvar uma nova tarefa`() {
        val novaTarefa =
            Tarefa(
                nome = "Aprender Spring Boot",
                custo = BigDecimal(100),
                dataLimite = LocalDate.now().plusDays(10),
                ordemApresentacao = 2,
            )

        val tarefaSalva = tarefaRepository.save(novaTarefa)
        assertNotNull(tarefaSalva)
        assertEquals(novaTarefa.nome, tarefaSalva.nome)
    }

    @Test
    fun `deve deletar uma tarefa`() {
        val tarefaParaDeletar = tarefaRepository.findByNome("Estudar Kotlin")
        assertNotNull(tarefaParaDeletar)

        tarefaRepository.delete(tarefaParaDeletar!!)
        val tarefaDeletada = tarefaRepository.findByNome("Estudar Kotlin")
        assertNull(tarefaDeletada)
    }

    @Test
    fun `deve encontrar a maior ordem de apresentacao`() {
        val tarefa1 =
            Tarefa(
                nome = "Primeira Tarefa",
                custo = BigDecimal(20),
                dataLimite = LocalDate.now().plusDays(5),
                ordemApresentacao = 1,
            )
        val tarefa2 =
            Tarefa(
                nome = "Segunda Tarefa",
                custo = BigDecimal(30),
                dataLimite = LocalDate.now().plusDays(6),
                ordemApresentacao = 3,
            )
        tarefaRepository.save(tarefa1)
        tarefaRepository.save(tarefa2)

        val maxOrdemApresentacao = tarefaRepository.findMaxOrdemApresentacao()

        assertNotNull(maxOrdemApresentacao)
        assertEquals(3, maxOrdemApresentacao)
    }
}
