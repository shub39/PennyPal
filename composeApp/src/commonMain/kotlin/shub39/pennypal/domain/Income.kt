package shub39.pennypal.domain

data class Income(
    val id: Long,
    val amount: Double,
    val recurrence: Recurrence,
    val title: String,
    val description: String?
)
