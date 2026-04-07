package shub39.pennypal.domain

import kotlin.time.Instant

data class Transaction(
    val id: Long,
    val categoryId: Long,
    val amount: Double,
    val date: Instant,
    val note: String?,
    val recurrence: Recurrence
)
