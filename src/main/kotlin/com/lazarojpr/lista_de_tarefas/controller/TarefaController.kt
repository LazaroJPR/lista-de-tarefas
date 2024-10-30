package com.lazarojpr.lista_de_tarefas.controller

import com.lazarojpr.lista_de_tarefas.model.Tarefa
import com.lazarojpr.lista_de_tarefas.service.TarefaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tarefas")
class TarefaController (private val tarefaService: TarefaService){

    @GetMapping
    fun listarTarefas(): ResponseEntity<List<Tarefa>> = ResponseEntity.ok(tarefaService.listarTarefas())

    @PostMapping
    fun criarTarefa(@RequestBody tarefa: Tarefa): ResponseEntity<Tarefa> = ResponseEntity.ok(tarefaService.salvarTarefa(tarefa))

    @PutMapping("/{id}")
    fun atualizarTarefa(@PathVariable id: Long, @RequestBody tarefa: Tarefa): ResponseEntity<Tarefa> =
        ResponseEntity.ok(tarefaService.atualizarTarefa(id, tarefa))

    @DeleteMapping("/{id}")
    fun excluirTarefa(@PathVariable id: Long): ResponseEntity<Void> {
        tarefaService.excluirTarefa(id)
        return ResponseEntity.noContent().build()
    }
}
