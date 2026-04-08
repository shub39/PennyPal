package shub39.pennypal.data.database

import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.TransactionType
import kotlin.time.Instant

data class TransactionEntity(
    val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val date: Instant,
    val note: String?,
    val recurrence: Recurrence,
    val transactionType: TransactionType,
)