package com.lazarojpr.lista_de_tarefas.controller

import com.lazarojpr.lista_de_tarefas.service.TarefaService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/tarefas")
class UIController(private val tarefaService: TarefaService) {

    @GetMapping("/home")
    fun home(model: Model): String {
        model.addAttribute("message", "Bem-vindo ao Thymeleaf!")
        model.addAttribute("tarefas", tarefaService.listarTarefas())
        return "tarefas"
    }

    @GetMapping("/new")
    fun newTarefa(model: Model): String {
        return "newTarefa" // Nome da página para criação da nova tarefa
    }
}
