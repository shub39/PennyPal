package shub39.pennypal.domain

data class Income(
    val id: Long,
    val amount: Double,
    val monthlyRecurring: Boolean,
    val title: String,
    val description: String?
)
