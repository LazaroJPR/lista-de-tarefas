package com.lazarojpr.lista_de_tarefas.service


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class TarefaServiceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `deve listar todas as tarefas`() {
        mockMvc.perform(get("/tarefas"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `deve criar uma nova tarefa`() {
        val novaTarefaJson = """
            {
                "nome": "Nova Tarefa",
                "custo": 200.0,
                "dataLimite": "${LocalDate.now().plusDays(10)}",
                "ordemApresentacao": 1
            }
        """

        mockMvc.perform(
            post("/tarefas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaTarefaJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nome").value("Nova Tarefa"))
    }
}