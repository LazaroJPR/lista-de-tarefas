package com.lazarojpr.lista_de_tarefas.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "tarefas")
data class Tarefa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(unique = true, nullable = false)
    val nome: String,
    @Column(nullable = false)
    val custo: BigDecimal,
    @Column(name = "data_limite", nullable = false)
    val dataLimite: LocalDate,
    @Column(name = "ordem_apresentacao", nullable = false)
    var ordemApresentacao: Int,
) {
    val custoFormatado: String
        get() = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(custo)
}
